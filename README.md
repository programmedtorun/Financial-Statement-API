# Financial-Statement-API

This program uses the [Financial Modeling Prep](https://financialmodelingprep.com/developer/docs/)
API to pull quarterly financial data from publically traded companies. 

The program was built for a professional day trader and returns a table of EPS and Sales data. Research and Development took less than 2 days for this project for the first version.  New features and modifications may be added on to this tool in the future should the developer and trader choose. 

Quarterly data is manipulated using the Json Google library GSON into a list. Same quarter previous year percentage changes are calculated/included in output data. 

## Running

This program has a CLI, and was distributed to the financial professional using a .jar file. The .jar artifact was built by the developer and sent to the trader. Further distribution is not intended. 

Code is built using JDK 13 and takes a command line argument, a stock ticker to run for example to display google. 

```java -jar Financial-Statement-API.jar goog```


``` 
*******************************************************************************************************
Getting Information for GOOG

    DATE                EPS             EPS(%Diff)              Revenue             Revenue(%Diff)
-------------------------------------------------------------------------------------------------------
2020-03-31		9.96		%3.97			41159000000		%13.26
2019-12-31		15.47		%20.11			46075000000		%17.31
2019-09-30		10.21		%-22.71			40499000000		%20.03
2019-06-30		14.33		%211.52			38944000000		%19.25
2019-03-31		9.58		%-29.19			36339000000		%16.67
2018-12-31		12.88		%394.06			39276000000		%21.51
2018-09-30		13.21		%36.05			33740000000		%21.49
2018-06-30		4.6 		%-9.63			32657000000		%25.56
```



 