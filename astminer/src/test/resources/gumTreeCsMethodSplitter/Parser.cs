using System;
using System.Collections;
using System.Collections.Generic;

namespace IDE_HW1.parser
{
    public class Parser
    {
        private static List<char> digits = new List<char>() {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        
        public static IExpression Parse(string s)
        {
            Stack<char> stack = new Stack<char>();
            ParseInputString(s, stack);
            return BuildExpression(stack);
        }

        private static void ParseInputString(string s, Stack<char> stack) 
        {
            foreach (var c in s)
            {
                if (c != ' ')
                {
                    stack.Push(c);
                }
            }
        }

        private static IExpression BuildExpression(Stack<char> stack, bool isInner = false)
        {
            IExpression expr = null;
            while (stack.Count > 0)
            {
                char c = stack.Pop();
                if (c == '+' || c == '-' || c == '*' || c == '/')
                {
                    expr = new BinaryExpression(BuildExpression(stack, true), expr, c.ToString());
                } else if (c == ')')
                {
                    expr = new ParenExpression(BuildExpression(stack));
                }
                else if (c == '(')
                {
                    return expr;
                }
                else
                {
                    expr = CreateLiteralOrVariable(c);
                }
                if (expr != null && isInner)
                {
                    return expr;
                }
            }
            return expr;
        }

        private static IExpression CreateLiteralOrVariable(char c) =>
            digits.Contains(c) ? new Literal(c.ToString()) : new Variable(c.ToString());
    }
}
