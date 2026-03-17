## Installation
### 1. Clone the repository
git clone https://github.com/uche-inyama/SupplyChainDigitalTwin.git
cd SupplyChainDigitalTwin

### 2 Install Python dependencies
cd agent
pip install anthropic

### 3. Set your Anthropic API key
#### Windows:
setx ANTHROPIC_API_KEY "your-api-key-here"
#### Mac/Linux:
export ANTHROPIC_API_KEY="your-api-key-here"

### 4. Running the Simulation
1. Open the AnyLogic project
Launch AnyLogic → File → Open → select SupplyChainDigitalTwin.alpx
2. Set experiment parameters in Main  
Parameter      &nbsp;&nbsp;&nbsp; || &nbsp;&nbsp;&nbsp;   Description &nbsp;&nbsp;&nbsp;     || &nbsp;&nbsp;&nbsp; Values  
demandScenario   &nbsp;&nbsp;&nbsp;  ||  &nbsp;&nbsp;&nbsp; Demand pattern   &nbsp;&nbsp;&nbsp; ||  &nbsp;&nbsp;&nbsp; 0 = Stable, 1 = Shock, 2 = Disruption  
autonomyCondition  &nbsp;&nbsp;&nbsp; ||  &nbsp;&nbsp;&nbsp; Autonomy level   &nbsp;&nbsp;&nbsp; ||  &nbsp;&nbsp;&nbsp;  0 = Baseline, 1 = HITL, 2 = Full Auto  
replicationNumber  &nbsp;&nbsp;&nbsp; ||  &nbsp;&nbsp;&nbsp; Replication number  &nbsp;&nbsp;&nbsp; ||  &nbsp;&nbsp;&nbsp; 1 to 5

### 5.  Set the autonomy condition
Condition  &nbsp;&nbsp;&nbsp; ||  &nbsp;&nbsp;&nbsp; agentEnabled  &nbsp;&nbsp;&nbsp; ||  &nbsp;&nbsp;&nbsp; HITL block in orderCycle   
A0 Baseline  &nbsp;&nbsp;&nbsp;  ||  &nbsp;&nbsp;&nbsp; false on all tiers  &nbsp;&nbsp;&nbsp;  ||  &nbsp;&nbsp;&nbsp;  Not applicable

### 6. Run
Click Run. The simulation runs for 100 weeks.
