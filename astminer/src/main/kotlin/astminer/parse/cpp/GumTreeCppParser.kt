package astminer.parse.cpp

import astminer.common.model.Parser
import com.github.gumtreediff.client.Run
import com.github.gumtreediff.gen.srcml.SrcmlCppTreeGenerator
import com.github.gumtreediff.tree.TreeContext
import java.io.InputStream
import java.io.InputStreamReader

class GumTreeCppParser : Parser<GumTreeCppNode> {
    init {
        Run.initGenerators()
    }

    override fun parseInputStream(content: InputStream): GumTreeCppNode? {
        val treeContext = SrcmlCppTreeGenerator().generate(InputStreamReader(content))
        return wrapGumTreeNode(treeContext)
    }
}

fun wrapGumTreeNode(treeContext: TreeContext): GumTreeCppNode {
    return GumTreeCppNode(treeContext.root, treeContext, null)
}
