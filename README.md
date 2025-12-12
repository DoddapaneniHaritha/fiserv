This is a Spring Boot application that demonstrates order management integrated with Apache Kafka for messaging.
It provides REST endpoints to create orders and publish them to a Kafka topic (order-topics). The application uses KafkaProducer and KafkaConsumer classes for message handling.
Sample REST API:
POST /orders/{customerId}
Content-Type: application/json

{
   "orderId": "1236",
    "amount":12.789,
    "status" :"Single2"
}
Response:
Order sent successfully!

Flow:

The request is sent to the Kafka Producer with the topic name order-topics.
Orders with the same customerId are sent to the same partition.
Consumers read and log data from the same topic. There are three consumers in different groups; if one consumer goes down, another takes over.
Finally, the data is stored in the database.

Git Link: git clone https://github.com/DoddapaneniHaritha/fiserv.git

Run zookeeper:
.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties


Run Kafka server
.\bin\windows\kafka-server-start.bat .\config\server.properties


Create a topic:
.\bin\windows\kafka-topics.bat --create --topic quickstart-events --bootstrap-server localhost:9092   


Curl: curl --location 'http://localhost:8081/orders/Customer6' \
--header 'Content-Type: application/json' \
--data '{
    "orderId": "1236",
    "amount":12.789,
    "status" :"Single2"

}'












Implemented	AcctMod Schema					Open Issue	Service Provider Schema											AcctModRq	AcctModRs	Blueprint	PCI/PII Data Fields																							
	XPATH	Description	Usage	DataType	Allowed Values		EFX Allowed Values	Service Provider Implementation Notes	Published Description	XPATH	Usage	DataType	Allowed Values	Transformation Rules	Default Value	List Table	Design Notes																											
X	AcctKeys	Account Identifier.  Uniquely identifies an account held at a financial institution. 	Required	Aggregate										If AcctKeys not provided, see exception 1 design notes			"Exception 1
HTTP 400-Bad Request
    ""Status"":        {  
      ""StatusCode"": ""1020"",         
      ""StatusDesc"": ""Required Element Not Included"",         
      ""Severity"": ""Error"",         
      ""SvcProviderName"": ""ESF"",        
       ""ServerStatusDesc"": ""Account keys is required""     
     }"	X		X					X																			
X	AcctKeys/AcctId	Account Identifier.  Uniquely identifies an account held at a financial institution. 	Required	NC-36				"For Portico, this field is concatenation of MemberNumber, AcctType and AcctId

Format: MemberNumberAcctTypeAcctId

AcctType for DDA, SDA, MMA, IRA = 'S'


Example: 123456789012S12"		"Account Id #1/ Account Id
M0MBRNBR/ Member Number  "	"Required
Required"	"string(38)
String (12)"		"EFX Exception Handeling:
If AcctId field is not present in request
    Then see the design notes- Exception 1 for error handling

Inspect AcctKeys/AcctId (AcctId)
If ""S"" is not present  
     Then see the design notes- Exception 2  for error handling

For Account Id #1/ Account Id: 
Move AcctKeys/AcctId (Member Number) ==> Account Id #1/ Account Id (starting in position 1, up to 12)
Replace first 'S' (AcctType) with '='
Move AcctKeys/AcctId (AcctId) --> after '='
Move ""bbbbbbbbbbbbbbbbbbbbbb"" -->  to remaining positions (up to 38)

For M0MBRNBR/ Member Number:
Move AcctKeys/AcctId (Member Number) ==> M0MBRNBR/ Member Number (without spaces)

If invalid value see design notes -Portico returns Exception 3
"			"c
"	X		X	X				X																			
X	AcctKeys/AcctType	Account Type.	Optional	ExtendedEnum	"CCA
RRA
LEAS
CDA
MLN
CLA
CRD
DDA
EQU
GLA
ILA
AdultCashISA
IRA
RET
EXT
JuniorCashISA
LIP
LOAN
LOC
CLOC
MLA
PBA
PORT
SDA
SDB
MMA
DDL"		"DDA
SDA
MMA
IRA


"	"DDA - Checking Account
SDA - Savings Account
MMA - Money Market Account 
IRA - Individual Retirement Account



"		See Transformation Rules.				"EFX Exception Handeling:
If AcctType field not present in request or not (DDA,SDA, MMA, IRA) or
If AcctType field = (DDA, SDA, MMA, IRA) and AcctKeys/AcctId not = ""S""  
    Then see the design notes- Exception1 for error handling






"		List_AcctType_Portico_INET	"AcctType not in Allowed Values  (or not sent):
Exception 1:
HTTP 400 - Bad Request
{
    ""Status"": {
        ""StatusCode"": ""1090"",
        ""StatusDesc"": ""Invalid Value"",
        ""Severity"": ""Error"",
        ""SvcProviderName"": ""ESF""
        ""ServerStatusDesc"": ""Invalid account type""
    }
} 

"	X							X																			
X	DepositAcctInfo/Nickname	Nickname. Indicates the nickname used by the party to more easily identify accounts.	Optional	C-80				Max provider length: 38		M0ACTNAM/Account Name	Optional	String (83)		"EFX ==> PRT

Validate Nickname. If > length 38 , see design notes Exception 1

/Account Type:
Move AcctKeys/AcctId (AcctType) ==> M0ACTNAM/Account Name/Account Type
/Account Indicator:
If AcctKeys/AcctType = SDA 
   Set Account Indicator = 10
If AcctKeys/AcctType = IRA 
   Set Account Indicator = 11
If AcctKeys/AcctType = DDA 
   Set Account Indicator = 20
If AcctKeys/AcctType = MMA 
   Set Account Indicator = 23
/Account Id:
Move AcctKeys/AcctId (Member Number) to Account Id (starting in position 1, up to 12)
AcctKeys/AcctId (AcctType), replace first 'S'  with '='
Move AcctKeys/AcctId (AcctId) after '='
Move ""bbbbbbbbbbbbbbbbbbbbbb"" -->  to remaining positions (up to 38)
/Account Name:
pass through"			"Exception 1
max length > 38  
HTTP 400 Bad Request
StatusCode: ""1080"",
StatusDesc: ""Invalid Length"",
Severity: ""Error"",
SvcProviderName: ""ESF""
ServerStatusDesc: ""Field value exceeds the max size for Nickname"""	X							X																			
X	DepositAcctInfo/Overdraft	"Overdraft.
"	Optional	Aggregate														X							X																			
X	DepositAcctInfo/OverdraftData	"Overdraft Data.
"	Optional	Aggregate														X							X																			
X	DepositAcctInfo/OverdraftData/OverdraftEnrollOpt	Overdraft Enroll Option. 	Optional	ExtendedEnum	"NoODPAccount
ODPAccount
OptIn
OptOut
InstutionOptOut
ImpliedOptOut"		"OptIn
OptOut"			M0REGEOD/Account Overdraft Option (Reg E)	Optional	String (44)		"EFX ==> PRT

Validate OverdraftEnrollOpt. If not valid value, see design notes Exception 1

/Account Type:
Move AcctKeys/AcctId (AcctType) ==> M0REGEOD/Account Overdraft Option/Account Type
/Account Indicator:
If AcctKeys/AcctType = SDA 
   Set Account Indicator = 10
If AcctKeys/AcctType = IRA 
   Set Account Indicator = 11
If AcctKeys/AcctType = DDA 
   Set Account Indicator = 20
If AcctKeys/AcctType = MMA 
   Set Account Indicator = 23
/Account Id:
Move AcctKeys/AcctId (Member Number) to Account Id (starting in position 1, up to 12)
AcctKeys/AcctId (AcctType), replace first 'S' with '='
Move AcctKeys/AcctId (AcctId) after '='
Move ""bbbbbbbbbbbbbbbbbbbbbb"" -->  to remaining positions (up to 38)
/Overdraft Option:
OptIn ==> Y
OptOut ==> N"			"If not valid value:
Exception 1:
HTTP 400 - Bad Request
{
    ""Status"": {
        ""StatusCode"": ""1090"",
        ""StatusDesc"": ""Invalid Value"",
        ""Severity"": ""Error"",
        ""SvcProviderName"": ""ESF""
        ""ServerStatusDesc"": ""Invalid overdraft enroll option""
    }
} "	X							X																			





