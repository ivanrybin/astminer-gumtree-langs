package astminer.examples

import astminer.common.model.LabeledPathContexts
import astminer.common.model.MethodInfo
import astminer.parse.cpp.GumTreeCppMethodSplitter
import astminer.parse.cpp.GumTreeCppNode
import astminer.parse.cpp.GumTreeCppParser
import astminer.paths.CsvPathStorage
import astminer.paths.PathMiner
import astminer.paths.PathRetrievalSettings
import astminer.paths.toPathContext
import java.io.File

private fun getCsvFriendlyMethodId(methodInfo: MethodInfo<GumTreeCppNode>): String {
    val className = methodInfo.enclosingElementName() ?: ""
    val methodName = methodInfo.name() ?: "unknown_method"
    val parameterTypes = methodInfo.methodParameters.joinToString("|") { it.name() ?: "_" }
    return "$className.$methodName($parameterTypes)"
}

fun allCppMethods() {
    val inputDir = "src/test/resources/gumTreeCppMethodSplitter"

    val miner = PathMiner(PathRetrievalSettings(5, 5))
    val outputDir = "out_examples/allCppMethods"
    val storage = CsvPathStorage(outputDir)

    File(inputDir).walkTopDown().filter {
        it.isFile && (it.absolutePath.endsWith(".cpp") || it.absolutePath.endsWith(".hpp"))
    }.forEach { file ->

        // parse file
        val fileNode = GumTreeCppParser().parseInputStream(file.inputStream()) ?: return@forEach

        // extract method nodes
        val methodNodes = GumTreeCppMethodSplitter().splitIntoMethods(fileNode)

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
