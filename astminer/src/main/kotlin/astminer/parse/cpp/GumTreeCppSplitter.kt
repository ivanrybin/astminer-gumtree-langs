package astminer.parse.cpp

import astminer.common.model.ElementNode
import astminer.common.model.MethodInfo
import astminer.common.model.MethodNode
import astminer.common.model.ParameterNode
import astminer.common.model.TreeMethodSplitter
import astminer.common.preOrder

class GumTreeCppMethodSplitter : TreeMethodSplitter<GumTreeCppNode> {

    companion object {
        // https://www.srcml.org/doc/cpp_srcML.html
        private object TypeLabels {
            const val classDefinition = "class"
            const val structDefinition = "struct"
            const val functionDefinition = "function"
            const val constructorDefinition = "constructor"
            const val destructorDefinition = "destructor"

            const val functionDeclaration = "function_decl"
            const val constructorDeclaration = "constructor_decl"
            const val destructorDeclaration = "destructor_decl"

            const val parameterList = "parameter_list"
            const val parameter = "parameter"
            const val decl = "decl"
            const val type = "type"
            const val name = "name"

            val classDefinitions = listOf(classDefinition, structDefinition)
            val functionDefinitions = listOf(functionDefinition, constructorDefinition, destructorDefinition)
            val functionDeclarations = listOf(functionDeclaration, constructorDeclaration, destructorDeclaration)
        }
    }

    override fun splitIntoMethods(root: GumTreeCppNode): Collection<MethodInfo<GumTreeCppNode>> {
        val methodRoots = root.preOrder().filter {
            TypeLabels.functionDefinitions.contains(it.getTypeLabel()) ||
                TypeLabels.functionDeclarations.contains(it.getTypeLabel())
        }
        return methodRoots.map { collectMethodInfo(it as GumTreeCppNode) }
    }

    private fun collectMethodInfo(methodNode: GumTreeCppNode): MethodInfo<GumTreeCppNode> {
        val methodReturnType = getElementType(methodNode)
        val methodName = getElementName(methodNode)

        val classRoot = getEnclosingClass(methodNode)
        val className = classRoot?.let { getElementName(it) }

        val parameters = getParameters(methodNode)

        return MethodInfo(
            MethodNode(methodNode, methodReturnType, methodName),
            ElementNode(classRoot, className),
            parameters
        )
    }

    private fun getElementName(node: GumTreeCppNode): GumTreeCppNode? {
        // some nodes have name in `name` node of depth 2 (<name><name>NAME<name/><name/>)
        if (TypeLabels.functionDefinitions.contains(node.getTypeLabel()) ||
            TypeLabels.functionDeclarations.contains(node.getTypeLabel()) ||
            TypeLabels.classDefinitions.contains(node.getTypeLabel())
        ) {
            return (
                node.getChildOfType(TypeLabels.name)?.getChildOfType(TypeLabels.name)
                    ?: node.getChildOfType(TypeLabels.name)
                ) as GumTreeCppNode?
        }

        return (
            node.getChildOfType(TypeLabels.name)
                ?: node.getChildOfType(TypeLabels.decl)?.getChildOfType(TypeLabels.name)
            ) as GumTreeCppNode?
    }

    private fun getElementType(node: GumTreeCppNode) = (
        node.getChildOfType(TypeLabels.type)
            ?: node.getChildOfType(TypeLabels.decl)?.getChildOfType(TypeLabels.type)
        ) as GumTreeCppNode?

    private fun getEnclosingClass(node: GumTreeCppNode): GumTreeCppNode? {
        if (TypeLabels.classDefinitions.contains(node.getTypeLabel())) {
            return node
        }
        val parentNode = node.getParent() as? GumTreeCppNode
        return parentNode?.let { getEnclosingClass(it) }
    }

    private fun getParameters(methodNode: GumTreeCppNode): List<ParameterNode<GumTreeCppNode>> {
        val params = methodNode.getChildren().filter {
            it.getTypeLabel() == TypeLabels.parameterList
        }.flatMap {
            it.getChildren()
        }.filter {
            it.getTypeLabel() == TypeLabels.parameter
        }
        return params.map {
            val node = it as GumTreeCppNode
            ParameterNode<GumTreeCppNode>(
                node,
                getElementType(node),
                getElementName(node)
            )
        }.toList()
    }
}
