void customerDemand()
{/*ALCODESTART::1772354370924*/
double demand = Math.max(0, normal(8, 2));
retailer.receiveOrder(demand);

customerDemandData.add(time(), demand);
/*ALCODEEND*/}

void endOfRun()
{/*ALCODESTART::1772535155690*/
calculateBullwhip();
/*ALCODEEND*/}

