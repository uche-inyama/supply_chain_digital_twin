void orderCycle()
{/*ALCODESTART::1772359623117*/
// Step 1: Fulfil what was ordered last period
double demand = incomingOrder + backlog;
double shipped = Math.min(demand, inventory);
inventory -= shipped;
backlog = Math.max(0, demand - shipped);

// Step 2: Forward shipment downstream (with lead time delay)
if (downstreamNode != null) {
    downstreamNode.create_deliveryShipment(leadTime, shipped);
} 

// Step 3: Calculate and place replenishment order upstream
double inventoryPosition = inventory - backlog;
outgoingOrder = Math.max(0, orderUpto - inventoryPosition);
if (upstreamNode != null) {
    upstreamNode.receiveOrder(outgoingOrder);
}

// Step 4: Accumulate cost
totalCost += 0.5 * Math.max(0, inventory) + 1.0 * Math.max(0, backlog);

inventoryData.add(time(), inventory);
backlogData.add(time(), backlog);
orderData.add(time(), outgoingOrder)
/*ALCODEEND*/}

