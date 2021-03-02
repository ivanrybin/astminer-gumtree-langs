package astminer.examples

import astminer.common.model.LabeledPathContexts
import astminer.common.model.MethodInfo
import astminer.parse.cs.GumTreeCsMethodSplitter
import astminer.parse.cs.GumTreeCsNode
import astminer.parse.cs.GumTreeCsParser
import astminer.paths.CsvPathStorage
import astminer.paths.PathMiner
import astminer.paths.PathRetrievalSettings
import astminer.paths.toPathContext
import java.io.File

private fun getCsvFriendlyMethodId(methodInfo: MethodInfo<GumTreeCsNode>): String {
    val className = methodInfo.enclosingElementName() ?: ""
    val methodName = methodInfo.name() ?: "unknown_method"
    val parameterTypes = methodInfo.methodParameters.joinToString("|") { it.name() ?: "_" }
    return "$className.$methodName($parameterTypes)"
}

fun allCsMethods() {
    val inputDir = "src/test/resources/gumTreeCsMethodSplitter"

    val miner = PathMiner(PathRetrievalSettings(5, 5))
    val outputDir = "out_examples/allCsMethods"
    val storage = CsvPathStorage(outputDir)

    File(inputDir).forFilesWithSuffix(".cs") { file ->

        // parse file
        val fileNode = GumTreeCsParser().parseInputStream(file.inputStream()) ?: return@forFilesWithSuffix

        // extract method nodes
        val methodNodes = GumTreeCsMethodSplitter().splitIntoMethods(fileNode)

        methodNodes.forEach { methodInfo ->
            // Retrieve paths from every node individually
            val paths = miner.retrievePaths(methodInfo.method.root)
            // Retrieve a method identifier
            val entityId = "${file.path}::${getCsvFriendlyMethodId(methodInfo)}"
            storage.store(LabeledPathContexts(entityId, paths.map { toPathContext(it) }))
        }
    }
    storage.close()
}
