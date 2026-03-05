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

// Forward shipment downstream
if (downstreamNode != null) {
    downstreamNode.receiveShipment(shipped);
}
/*ALCODEEND*/}

