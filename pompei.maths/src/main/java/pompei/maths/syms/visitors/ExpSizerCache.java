package pompei.maths.syms.visitors;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import pompei.maths.syms.top.Expr;
import pompei.maths.syms.top.Visitor;

public class ExpSizerCache implements InvocationHandler {
  
  private ExpSizer target;
  private Map<String, PaintSize> cache = new HashMap<>();
  
  private ExpSizerCache(ExpSizer target) {
    this.target = target;
  }
  
  @SuppressWarnings("rawtypes")
  public static ExpSizer cacheIt(ExpSizer target) {
    ClassLoader cl = target.getClass().getClassLoader();
    Class[] interfaces = new Class[] { Visitor.class };
    ExpSizerCache handler = new ExpSizerCache(target);
    
    return (ExpSizer)Proxy.newProxyInstance(cl, interfaces, handler);
  }
  
  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    if (isVisitMethod(method)) return callVisitMethod(method, args);
    return method.invoke(target, args);
  }
  
  private Object callVisitMethod(Method method, Object[] args) throws IllegalAccessException,
      InvocationTargetException {
    Expr expr = (Expr)args[0];
    String id = System.identityHashCode(expr) + "-" + target.level;
    {
      PaintSize ret = cache.get(id);
      if (ret != null) return ret;
    }
    {
      Object object = method.invoke(target, args);
      if (object instanceof PaintSize) {
        PaintSize ret = (PaintSize)object;
        cache.put(id, ret);
        return ret;
      }
      throw new RuntimeException("Method " + method + " for " + args[0] + " returns not PaintSize");
    }
  }
  
  private boolean isVisitMethod(Method method) {
    Class<?>[] pt = method.getParameterTypes();
    return method.getName().startsWith("visit") && pt.length == 1
        && pt[0].isAssignableFrom(Expr.class);
  }
}
