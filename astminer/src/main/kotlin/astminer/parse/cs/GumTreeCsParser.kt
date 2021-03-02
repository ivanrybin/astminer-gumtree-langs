package astminer.parse.cs

import astminer.common.model.Parser
import com.github.gumtreediff.client.Run
import com.github.gumtreediff.gen.srcml.SrcmlCsTreeGenerator
import com.github.gumtreediff.tree.TreeContext
import java.io.InputStream
import java.io.InputStreamReader

class GumTreeCsParser : Parser<GumTreeCsNode> {
    init {
        Run.initGenerators()
    }

    override fun parseInputStream(content: InputStream): GumTreeCsNode? {
        val treeContext = SrcmlCsTreeGenerator().generate(InputStreamReader(content))
        return wrapGumTreeNode(treeContext)
    }
}

fun wrapGumTreeNode(treeContext: TreeContext): GumTreeCsNode {
    return GumTreeCsNode(treeContext.root, treeContext, null)
}
