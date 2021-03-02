package astminer.examples

import astminer.common.model.MethodInfo
import astminer.parse.javascript.GumTreeJavaScriptNode
import astminer.parse.javascript.GumTreeJavaScriptParser
import astminer.paths.CsvPathStorage
import astminer.paths.PathMiner
import astminer.paths.PathRetrievalSettings
import java.io.File

private fun getCsvFriendlyMethodId(methodInfo: MethodInfo<GumTreeJavaScriptNode>): String {
    val className = methodInfo.enclosingElementName() ?: ""
    val methodName = methodInfo.name() ?: "unknown_method"
    val parameterTypes = methodInfo.methodParameters.joinToString("|") { it.name() ?: "_" }
    return "$className.$methodName($parameterTypes)"
}

// Retrieve paths from all Java files, using a GumTree parser.
// GumTreeMethodSplitter is used to extract individual method nodes from the compilation unit tree.
fun allJavaScriptMethods() {
    val inputDir = "src/test/resources/gumTreeJavaScriptMethodSplitter"

    val miner = PathMiner(PathRetrievalSettings(5, 5))
    val outputDir = "out_examples/allJavaScriptMethods"
    val storage = CsvPathStorage(outputDir)

    File(inputDir).forFilesWithSuffix(".js") { file ->
        println(file)
        // parse file
        val fileNode = GumTreeJavaScriptParser().parseInputStream(file.inputStream()) ?: return@forFilesWithSuffix


        val x = 15;
        // extract method nodes
//        val methodNodes = GumTreeMethodSplitter().splitIntoMethods(fileNode)
//
//        methodNodes.forEach { methodInfo ->
//            //Retrieve paths from every node individually
//            val paths = miner.retrievePaths(methodInfo.method.root)
//            //Retrieve a method identifier
//            val entityId = "${file.path}::${getCsvFriendlyMethodId(methodInfo)}"
//            storage.store(LabeledPathContexts(entityId, paths.map { toPathContext(it) }))
//        }
    }

    storage.close()
}
