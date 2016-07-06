package pompei.maths.syms2.model.display;

import pompei.maths.syms2.model.display.impl.DisplayBrackets;
import pompei.maths.syms2.model.display.impl.DisplayDiv;
import pompei.maths.syms2.model.display.impl.DisplayLeaning;
import pompei.maths.syms2.model.display.impl.DisplayText;
import pompei.maths.syms2.model.universe.*;

import java.awt.Color;
import java.util.LinkedList;

import static pompei.maths.syms2.model.display.BracketsType.SQUARE;
import static pompei.maths.syms2.model.display.impl.DisplayLeaning.displayOrder;

public class DisplayExprVisitor implements ExprVisitor<DisplayExpr> {

  /**
   * Уровень вложенности. Чем он больше. тем меньшим размером необходимо оттображать
   */
  private int level = 0;

  @Override
  public DisplayExpr visitExprInt(ExprInt exprInt) {
    return new DisplayText(level, "" + exprInt.value, Color.BLUE, true, false);
  }

  @Override
  public DisplayExpr visitExprVar(ExprVar exprVar) {
    return new DisplayText(level, "" + exprVar.name, new Color(69, 126, 217), false, false);
  }

  @Override
  public DisplayExpr visitExprOper1(ExprOper1 exprOper1) {
    DisplayExpr operand = exprOper1.operand.visit(this);
    switch (exprOper1.oper1) {
      case Plus:
        return getDisplayTextOper(null, "+", operand);
      case Minus:
        return getDisplayTextOper(null, "-", operand);
    }
    throw new IllegalArgumentException("Cannot display oper1 " + exprOper1.oper1);
  }

  @Override
  public DisplayExpr visitExprOper2(ExprOper2 exprOper2) {
    if (exprOper2.oper2 == Oper2.Power) {
      DisplayExpr left = exprOper2.left.visit(this);
      level++;
      DisplayExpr right = exprOper2.right.visit(this);
      level--;
      return new DisplayLeaning(left, right, true, 0.9);
    }

    {
      DisplayExpr left = exprOper2.left.visit(this);
      DisplayExpr right = exprOper2.right.visit(this);
      switch (exprOper2.oper2) {
        case Plus:
          return getDisplayTextOper(left, "+", right);
        case Minus:
          return getDisplayTextOper(left, "-", right);
        case Mul:
          return getDisplayTextOper(left, "⋅", right);
        case Div:
          //noinspection SuspiciousNameCombination
          return new DisplayDiv(level, left, right);
      }
      throw new IllegalArgumentException("Cannot display oper2 " + exprOper2.oper2);
    }
  }

  private DisplayExpr getDisplayTextOper(DisplayExpr left, String operText, DisplayExpr right) {
    DisplayText displayOperText = new DisplayText(level, operText, new Color(0, 0, 0), false, false);

    if (left != null && right != null) return displayOrder(left, displayOperText, right);

    if (left != null) return displayOrder(left, displayOperText);

    if (right != null) return displayOrder(displayOperText, right);

    throw new RuntimeException("Must be right != null or/and left != null");
  }

  @Override
  public DisplayExpr visitExprFunc1(ExprFunc1 exprFunc1) {
    DisplayExpr arg = exprFunc1.arg.visit(this);
    String name = exprFunc1.func1.name();
    return getDisplayFunc(name, arg);
  }

  private DisplayExpr getDisplayFunc(String name, DisplayExpr... args) {
    return displayOrder(
        new DisplayText(level, name, new Color(0, 0, 0), false, false),
        new DisplayBrackets(displayWithComma(args), SQUARE, new Color(0, 0, 0))
    );
  }

  private DisplayExpr displayWithComma(DisplayExpr[] args) {
    if (args.length == 1) return args[0];

    LinkedList<DisplayExpr> list = new LinkedList<>();
    for (DisplayExpr arg : args) {
      list.add(arg);
      list.add(new DisplayText(level, ", ", new Color(0, 0, 0), false, false));
    }
    list.removeLast();
    return displayOrder(list);
  }

  @Override
  public DisplayExpr visitExprFunc2(ExprFunc2 exprFunc2) {
    return getDisplayFunc(exprFunc2.func2.name(),
        exprFunc2.arg1.visit(this), exprFunc2.arg2.visit(this)
    );
  }

  @Override
  public DisplayExpr visitBrackets(Brackets brackets) {
    return new DisplayBrackets(brackets.in.visit(this), BracketsType.ROUND, new Color(0, 0, 0));
  }
}
