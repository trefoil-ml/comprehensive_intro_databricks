{"version":"NotebookV1","origId":1745794912675204,"name":"Dataset-Mounts","language":"scala","commands":[{"version":"CommandV1","origId":1745794912675205,"guid":"dcc412de-276b-42df-a0cd-95b9afa528e4","subtype":"command","commandType":"auto","position":1.0,"command":"%scala\nval tags = com.databricks.logging.AttributionContext.current.tags\n\n//*******************************************\n// GET VERSION OF APACHE SPARK\n//*******************************************\n\n// Get the version of spark\nval Array(sparkMajorVersion, sparkMinorVersion, _) = spark.version.split(\"\"\"\\.\"\"\")\n\n// Set the major and minor versions\nspark.conf.set(\"com.databricks.training.spark.major-version\", sparkMajorVersion)\nspark.conf.set(\"com.databricks.training.spark.minor-version\", sparkMinorVersion)\n\n//*******************************************\n// GET VERSION OF DATABRICKS RUNTIME\n//*******************************************\n\n// Get the version of the Databricks Runtime\nval runtimeVersion = tags.collect({ case (t, v) if t.name == \"sparkVersion\" => v }).head\nval runtimeVersions = runtimeVersion.split(\"\"\"-\"\"\")\nval (dbrVersion, scalaVersion) = if (runtimeVersions.size == 3) {\n  val Array(dbrVersion, _, scalaVersion) = runtimeVersions\n  (dbrVersion, scalaVersion.replace(\"scala\", \"\"))\n} else {\n  val Array(dbrVersion, scalaVersion) = runtimeVersions\n  (dbrVersion, scalaVersion.replace(\"scala\", \"\"))\n}\nval Array(dbrMajorVersion, dbrMinorVersion, _) = dbrVersion.split(\"\"\"\\.\"\"\")\n\n// Set the the major and minor versions\nspark.conf.set(\"com.databricks.training.dbr.major-version\", dbrMajorVersion)\nspark.conf.set(\"com.databricks.training.dbr.minor-version\", dbrMinorVersion)\n\n//*******************************************\n// GET USERNAME AND USERHOME\n//*******************************************\n\n// Get the user's name\nval username = tags.getOrElse(com.databricks.logging.BaseTagDefinitions.TAG_USER, java.util.UUID.randomUUID.toString.replace(\"-\", \"\"))\nval userhome = s\"dbfs:/user/$username\"\n\n// Set the user's name and home directory\nspark.conf.set(\"com.databricks.training.username\", username)\nspark.conf.set(\"com.databricks.training.userhome\", userhome)\n\n//**********************************\n// VARIOUS UTILITY FUNCTIONS\n//**********************************\n\ndef assertSparkVersion(expMajor:Int, expMinor:Int):Unit = {\n  val major = spark.conf.get(\"com.databricks.training.spark.major-version\")\n  val minor = spark.conf.get(\"com.databricks.training.spark.minor-version\")\n\n  if ((major.toInt < expMajor) || (major.toInt == expMajor && minor.toInt < expMinor))\n    throw new Exception(s\"This notebook must be ran on Spark version $expMajor.$expMinor or better, found Spark $major.$minor\")\n}\n\ndef assertDbrVersion(expMajor:Int, expMinor:Int):Unit = {\n  val major = spark.conf.get(\"com.databricks.training.dbr.major-version\")\n  val minor = spark.conf.get(\"com.databricks.training.dbr.minor-version\")\n\n  if ((major.toInt < expMajor) || (major.toInt == expMajor && minor.toInt < expMinor))\n    throw new Exception(s\"This notebook must be ran on Databricks Runtime (DBR) version $expMajor.$expMinor or better, found $major.$minor.\")\n}\n\n//*******************************************\n// CHECK FOR REQUIRED VERIONS OF SPARK & DBR\n//*******************************************\n\nassertDbrVersion(4, 0)\nassertSparkVersion(2, 3)\n\ndisplayHTML(\"Initialized classroom variables & functions...\")","commandVersion":0,"state":"finished","results":null,"errorSummary":null,"error":null,"workflows":[],"startTime":0,"submitTime":0,"finishTime":0,"collapsed":false,"bindings":{},"inputWidgets":{},"displayType":"table","width":"auto","height":"auto","xColumns":null,"yColumns":null,"pivotColumns":null,"pivotAggregation":null,"customPlotOptions":{},"commentThread":[],"commentsVisible":false,"parentHierarchy":[],"diffInserts":[],"diffDeletes":[],"globalVars":{},"latestUser":"","latestUserId":"4258953226069350","commandTitle":"","showCommandTitle":false,"hideCommandCode":false,"hideCommandResult":false,"iPythonMetadata":null,"streamStates":{},"datasetPreviewNameToCmdIdMap":{},"nuid":"36c2dbb1-8ac9-4028-b87e-35b65caa82bb"},{"version":"CommandV1","origId":1745794912675206,"guid":"b0f4a86a-02e1-4405-bd3b-272bd75d4b54","subtype":"command","commandType":"auto","position":2.0,"command":"%python\n\n#**********************************\n# VARIOUS UTILITY FUNCTIONS\n#**********************************\n\ndef assertSparkVersion(expMajor, expMinor):\n  major = spark.conf.get(\"com.databricks.training.spark.major-version\")\n  minor = spark.conf.get(\"com.databricks.training.spark.minor-version\")\n\n  if (int(major) < expMajor) or (int(major) == expMajor and int(minor) < expMinor):\n    msg = \"This notebook must run on Spark version {}.{} or better, found.\".format(expMajor, expMinor, major, minor)\n    raise Exception(msg)\n\ndef assertDbrVersion(expMajor, expMinor):\n  major = spark.conf.get(\"com.databricks.training.dbr.major-version\")\n  minor = spark.conf.get(\"com.databricks.training.dbr.minor-version\")\n\n  if (int(major) < expMajor) or (int(major) == expMajor and int(minor) < expMinor):\n    msg = \"This notebook must run on Databricks Runtime (DBR) version {}.{} or better, found.\".format(expMajor, expMinor, major, minor)\n    raise Exception(msg)\n\n#**********************************\n# INIT VARIOUS VARIABLES\n#**********************************\n\nusername = spark.conf.get(\"com.databricks.training.username\")\nuserhome = spark.conf.get(\"com.databricks.training.userhome\")\n\nNone # suppress output","commandVersion":0,"state":"finished","results":null,"errorSummary":null,"error":null,"workflows":[],"startTime":0,"submitTime":0,"finishTime":0,"collapsed":false,"bindings":{},"inputWidgets":{},"displayType":"table","width":"auto","height":"auto","xColumns":null,"yColumns":null,"pivotColumns":null,"pivotAggregation":null,"customPlotOptions":{},"commentThread":[],"commentsVisible":false,"parentHierarchy":[],"diffInserts":[],"diffDeletes":[],"globalVars":{},"latestUser":"","latestUserId":"4258953226069350","commandTitle":"","showCommandTitle":false,"hideCommandCode":false,"hideCommandResult":false,"iPythonMetadata":null,"streamStates":{},"datasetPreviewNameToCmdIdMap":{},"nuid":"eb5856c0-bfbe-4ae7-8bb2-7be072bd10a9"},{"version":"CommandV1","origId":1745794912675207,"guid":"eb25dafb-2ff1-4588-97e7-88cc63527c9d","subtype":"command","commandType":"auto","position":3.0,"command":"%scala\n\n//**********************************\n// CREATE THE MOUNTS\n//**********************************\n\ndef getAwsRegion():String = {\n  try {\n    import scala.io.Source\n    import scala.util.parsing.json._\n\n    val jsonString = Source.fromURL(\"http://169.254.169.254/latest/dynamic/instance-identity/document\").mkString // reports ec2 info\n    val map = JSON.parseFull(jsonString).getOrElse(null).asInstanceOf[Map[Any,Any]]\n    map.getOrElse(\"region\", null).asInstanceOf[String]\n\n  } catch {\n    // We will use this later to know if we are Amazon vs Azure\n    case _: java.io.FileNotFoundException => null\n  }\n}\n\ndef getAzureRegion():String = {\n  import com.databricks.backend.common.util.Project\n  import com.databricks.conf.trusted.ProjectConf\n  import com.databricks.backend.daemon.driver.DriverConf\n\n  new DriverConf(ProjectConf.loadLocalConfig(Project.Driver)).region\n}\n\ndef getAwsMapping(region:String):(String,Map[String,String]) = {\n  val awsAccessKey = \"AKIAJBRYNXGHORDHZB4A\"\n  val awsSecretKey = \"a0BzE1bSegfydr3%2FGE3LSPM6uIV5A4hOUfpH8aFF\"\n\n  val MAPPINGS = Map(\n    \"us-west-2\"      -> (s\"s3a://${awsAccessKey}:${awsSecretKey}@databricks-corp-training/common\", Map[String,String]()),\n    \"_default\"       -> (s\"s3a://${awsAccessKey}:${awsSecretKey}@databricks-corp-training/common\", Map[String,String]())\n  )\n\n  MAPPINGS.getOrElse(region, MAPPINGS(\"_default\"))\n}\n\ndef getAzureMapping(region:String):(String,Map[String,String]) = {\n\n  // Databricks only wants the query-string portion of the SAS URL (i.e., the part from the \"?\" onward, including\n  // the \"?\"). But it's easier to copy-and-paste the full URL from the Azure Portal. So, that's what we do.\n  // The logic, below, converts these URLs to just the query-string parts.\n\n  val EastAsiaAcct = \"dbtraineastasia\"\n  val EastAsiaSas = \"https://dbtraineastasia.blob.core.windows.net/?sv=2017-07-29&ss=b&srt=sco&sp=rl&se=2023-04-19T05:02:54Z&st=2018-04-18T21:02:54Z&spr=https&sig=gfu42Oi3QqKjDUMOBGbayQ9WUsxEQ4EdHpI%2BRBCWPig%3D\"\n\n  val EastUSAcct = \"dbtraineastus\"\n  val EastUSSas = \"https://dbtraineastus.blob.core.windows.net/?sv=2017-07-29&ss=b&srt=sco&sp=rl&se=2023-04-19T06:29:20Z&st=2018-04-18T22:29:20Z&spr=https&sig=drx0LE2W%2BrUTvblQtVU4SiRlWk1WbLUJI6nDvFWIfHA%3D\"\n\n  val EastUS2Acct = \"dbtraineastus2\"\n  val EastUS2Sas = \"https://dbtraineastus2.blob.core.windows.net/?sv=2017-07-29&ss=b&srt=sco&sp=rl&se=2023-04-19T06:32:30Z&st=2018-04-18T22:32:30Z&spr=https&sig=BB%2FQzc0XHAH%2FarDQhKcpu49feb7llv3ZjnfViuI9IWo%3D\"\n\n  val NorthCentralUSAcct = \"dbtrainnorthcentralus\"\n  val NorthCentralUSSas = \"https://dbtrainnorthcentralus.blob.core.windows.net/?sv=2017-07-29&ss=b&srt=sco&sp=rl&se=2023-04-19T06:35:29Z&st=2018-04-18T22:35:29Z&spr=https&sig=htJIax%2B%2FAYQINjERk0z%2B0jR%2BBF8MpPK3BdBFa8%2FLAUU%3D\"\n\n  val NorthEuropeAcct = \"dbtrainnortheurope\"\n  val NorthEuropeSas = \"https://dbtrainnortheurope.blob.core.windows.net/?sv=2017-07-29&ss=b&srt=sco&sp=rl&se=2023-04-19T06:37:15Z&st=2018-04-18T22:37:15Z&spr=https&sig=upIQ%2FoMa4v2aRB8AAB3gBY%2BvybhLwQGS2%2Bsyq0Z3mZw%3D\"\n\n  val SouthCentralUSAcct = \"dbtrainsouthcentralus\"\n  val SouthCentralUSSas = \"https://dbtrainsouthcentralus.blob.core.windows.net/?sv=2017-07-29&ss=b&srt=sco&sp=rl&se=2023-04-19T06:38:27Z&st=2018-04-18T22:38:27Z&spr=https&sig=OL2amlrWn4X9ABAoWyvaL%2FVIf83GVrAnRL6gpauxqzA%3D\"\n\n  val SouthEastAsiaAcct = \"dbtrainsoutheastasia\"\n  val SouthEastAsiaSas = \"https://dbtrainsoutheastasia.blob.core.windows.net/?sv=2017-07-29&ss=b&srt=sco&sp=rl&se=2023-04-19T06:39:59Z&st=2018-04-18T22:39:59Z&spr=https&sig=9LFC3cZXe4qWMGABmu%2BuMEAsSKGB%2BfxO0kZTxDAhvF8%3D\"\n\n  val WestCentralUSAcct = \"dbtrainwestcentralus\"\n  val WestCentralUSSas = \"https://dbtrainwestcentralus.blob.core.windows.net/?sv=2017-07-29&ss=b&srt=sco&sp=rl&se=2023-04-19T06:33:55Z&st=2018-04-18T22:33:55Z&spr=https&sig=5tZWw9V4pYuFu7sjTmEcFujAJlcVg3hBl1jgWcSB3Qg%3D\"\n\n  val WestEuropeAcct = \"dbtrainwesteurope\"\n  val WestEuropeSas = \"https://dbtrainwesteurope.blob.core.windows.net/?sv=2017-07-29&ss=b&srt=sco&sp=rl&se=2023-04-19T13:30:09Z&st=2018-04-19T05:30:09Z&spr=https&sig=VRX%2Fp6pC3jJsrPoR7Lz8kvFAUhJC1%2Fzye%2FYvvgFbD5E%3D\"\n\n  val WestUSAcct = \"dbtrainwestus\"\n  val WestUSSas = \"https://dbtrainwestus.blob.core.windows.net/?sv=2017-07-29&ss=b&srt=sco&sp=rl&se=2023-04-19T13:31:40Z&st=2018-04-19T05:31:40Z&spr=https&sig=GRH1J%2FgUiptQHYXLX5JmlICMCOvqqshvKSN4ygqFc3Q%3D\"\n\n  val WestUS2Acct = \"dbtrainwestus2\"\n  val WestUS2Sas = \"https://dbtrainwestus2.blob.core.windows.net/?sv=2017-07-29&ss=b&srt=sco&sp=ra&se=2023-04-19T13:32:45Z&st=2018-04-19T05:32:45Z&spr=https&sig=TJpU%2FHaVkDNiY%2B9zyyjBDt8GKadRvwnFArG2q8JXyhY%3D\"\n\n  // For each Azure region we support, associate an appropriate Storage Account and SAS token\n  // to use to mount /mnt/training (so that we use the version that's closest to the\n  // region containing the Databricks instance.)\n  // FUTURE RELEASE: New regions are rolled back for this release.  Test new regions before deployment\n\n  var MAPPINGS = Map(\n//     \"EastAsia\"         -> (EastAsiaAcct, EastAsiaSas),\n//     \"EastUS\"           -> (EastUSAcct, EastUSSas),\n//     \"EastUS2\"          -> (EastUS2Acct, EastUS2Sas),\n//     \"NorthCentralUS\"   -> (NorthCentralUSAcct, NorthCentralUSSas),\n//     \"NorthEurope\"      -> (NorthEuropeAcct, NorthEuropeSas),\n//     \"SouthCentralUS\"   -> (SouthCentralUSAcct, SouthCentralUSSas),\n//     \"SouthEastAsia\"    -> (SouthEastAsiaAcct, SouthEastAsiaSas),\n//     \"WestCentralUS\"    -> (WestCentralUSAcct, WestCentralUSSas),\n//     \"WestEurope\"       -> (WestEuropeAcct, WestEuropeSas),\n//     \"WestUS\"           -> (WestUSAcct, WestUSSas),\n    \"WestUS2\"          -> (WestUS2Acct, WestUS2Sas),\n    \"_default\"         -> (EastUS2Acct, EastUS2Sas)\n  ).map { case (key, (acct, url)) => key -> (acct, url.slice(url.indexOf('?'), url.length)) }\n\n  val (account: String, sasKey: String) = MAPPINGS.getOrElse(region, MAPPINGS(\"_default\"))\n\n  val blob = \"training\"\n  val source = s\"wasbs://$blob@$account.blob.core.windows.net/\"\n  val configMap = Map(\n    s\"fs.azure.sas.$blob.$account.blob.core.windows.net\" -> sasKey\n  )\n\n  (source, configMap)\n}\n\ndef mount(source: String, extraConfigs:Map[String,String], mountPoint: String): Unit = {\n  try {\n    dbutils.fs.mount(source=source,\n                     mountPoint=mountPoint,\n                     extraConfigs=extraConfigs)\n  } catch {\n    case ioe: java.lang.IllegalArgumentException => try { // Mount with IAM roles instead of keys for PVC\n      dbutils.fs.mount(\n        source,\n        mountPoint\n      )} catch {\n      case e: Exception =>\n        println(s\"*** ERROR: Unable to mount $mountPoint: ${e.getMessage}\")\n    }\n    case e: Exception =>\n      println(s\"*** ERROR: Unable to mount $mountPoint: ${e.getMessage}\")\n  }\n}\n\ndef autoMount(): Unit = {\n\n  var awsRegion = getAwsRegion()\n  val (source, extraConfigs) = if (awsRegion != null)  {\n    spark.conf.set(\"com.databricks.training.region.name\", awsRegion)\n    getAwsMapping(awsRegion)\n\n  } else {\n    val azureRegion = getAzureRegion()\n    spark.conf.set(\"com.databricks.training.region.name\", azureRegion)\n    getAzureMapping(azureRegion)\n  }\n\n  val mountDir = \"/mnt/training\"\n  if (dbutils.fs.mounts().map(_.mountPoint).contains(mountDir)) {\n    println(s\"Already mounted $source\\n to $mountDir\")\n  } else {\n    println(s\"Mounting $source\\n to $mountDir\")\n    mount(source, extraConfigs, mountDir)\n  }\n}\n\nautoMount()\n\nprintln(\"-\"*80)\ndisplayHTML(\"Mounted data sets to '/mnt/training' ...\")","commandVersion":0,"state":"finished","results":null,"errorSummary":null,"error":null,"workflows":[],"startTime":0,"submitTime":0,"finishTime":0,"collapsed":false,"bindings":{},"inputWidgets":{},"displayType":"table","width":"auto","height":"auto","xColumns":null,"yColumns":null,"pivotColumns":null,"pivotAggregation":null,"customPlotOptions":{},"commentThread":[],"commentsVisible":false,"parentHierarchy":[],"diffInserts":[],"diffDeletes":[],"globalVars":{},"latestUser":"","latestUserId":"4258953226069350","commandTitle":"","showCommandTitle":false,"hideCommandCode":false,"hideCommandResult":false,"iPythonMetadata":null,"streamStates":{},"datasetPreviewNameToCmdIdMap":{},"nuid":"93f96bef-0954-47fe-bc17-144f30640e09"}],"dashboards":[],"guid":"14b647a7-2616-4a8c-98c7-77bf129711fc","globalVars":{},"iPythonMetadata":null,"inputWidgets":{}}