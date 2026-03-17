double receiveOrder(double amount)
{/*ALCODESTART::1772352191162*/
incomingOrder = amount;


/*ALCODEEND*/}

double receiveShipment(double amount)
{/*ALCODESTART::1772353228374*/
// Receive incoming stock
inventory += amount;

// Calculate total demand to fulfil
double demand = incomingOrder + backlog;

// Ship what you can
double shipped = Math.min(demand, inventory);
inventory -= shipped;

// Whatever couldn't be shipped becomes backlog
backlog = Math.max(0, demand - shipped);

incomingOrder = 0;

// Forward shipment downstream
if (downstreamNode != null) {
    downstreamNode.receiveShipment(shipped);
}
/*ALCODEEND*/}

double callLLMAgent()
{/*ALCODESTART::1772751311292*/
// ── Write state.json for Python ───────────────────────────
String stateDir = agentScriptPath.replace("agent.py", "");
String statePath = stateDir + "state.json";
String responsePath = stateDir + "response.json";

String stateJson = String.format(
    "{\"tier\":\"%s\",\"inventory\":%.2f,\"backlog\":%.2f," +
    "\"orderUpTo\":%d,\"simTime\":%.2f,\"lastDemand\":%.2f,\"leadTime\":%d}",
    tierName, inventory, backlog, orderUpTo, time(), lastDemand, leadTime
);

try {
    // Write state file
    java.io.FileWriter fw = new java.io.FileWriter(statePath);
    fw.write(stateJson);
    fw.close();

    // Call Python script
    ProcessBuilder pb = new ProcessBuilder("python", agentScriptPath);
    pb.redirectErrorStream(true);
    Process process = pb.start();

    // Wait up to 10 seconds for response
    boolean finished = process.waitFor(10, java.util.concurrent.TimeUnit.SECONDS);

    if (!finished) {
        traceln("[" + tierName + "] WARNING: Python agent timed out — using rule-based fallback");
        process.destroyForcibly();
        return Math.max(0, orderUpTo - (inventory - backlog));
    }

    // Read response file
    java.nio.file.Path rPath = java.nio.file.Paths.get(responsePath);
    String responseJson = new String(java.nio.file.Files.readAllBytes(rPath));

    // Parse JSON manually (no external library needed)
    agentConfidence = parseJsonDouble(responseJson, "confidence");
    agentReasoning  = parseJsonString(responseJson, "reasoning");
    double orderQty = parseJsonDouble(responseJson, "order_quantity");

    traceln("[" + tierName + "] t=" + time() + 
            " → LLM order=" + orderQty + 
            " confidence=" + agentConfidence + 
            " | " + agentReasoning);

    return Math.max(0, orderQty);

} catch (Exception e) {
    traceln("[" + tierName + "] ERROR calling LLM agent: " + e.getMessage());
    return Math.max(0, orderUpTo - (inventory - backlog));
}
/*ALCODEEND*/}

double parseJsonDouble(String json,String key)
{/*ALCODESTART::1772751764897*/
try {
    String search = "\"" + key + "\":";
    int idx = json.indexOf(search);
    if (idx == -1) return 0.0;
    int start = idx + search.length();
    int end = json.indexOf(",", start);
    if (end == -1) end = json.indexOf("}", start);
    return Double.parseDouble(json.substring(start, end).trim());
} catch (Exception e) {
    return 0.0;
}
/*ALCODEEND*/}

String parseJsonString(String json,String key)
{/*ALCODESTART::1772751770055*/
try {
    String search = "\"" + key + "\":\"";
    int idx = json.indexOf(search);
    if (idx == -1) return "";
    int start = idx + search.length();
    int end = json.indexOf("\"", start);
    return json.substring(start, end);
} catch (Exception e) {
    return "";
}
/*ALCODEEND*/}

