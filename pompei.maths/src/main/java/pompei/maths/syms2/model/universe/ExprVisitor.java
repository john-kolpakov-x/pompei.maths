package pompei.maths.syms2.model.universe;

public interface ExprVisitor<T> {
  T visitExprInt(ExprInt exprInt);

  T visitExprVar(ExprVar exprVar);

  T visitExprOper1(ExprOper1 exprOper1);

  T visitExprOper2(ExprOper2 exprOper2);

  T visitExprFunc2(ExprFunc2 exprFunc2);

  T visitExprFunc1(ExprFunc1 exprFunc1);

  T visitBrackets(Brackets brackets);

}
