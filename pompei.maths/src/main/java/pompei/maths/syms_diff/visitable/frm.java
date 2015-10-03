package pompei.maths.syms_diff.visitable;

import java.math.BigInteger;

import pompei.maths.syms_diff.model.Form;

public class frm {
  private static Form var(String s) {
    String[] split = s.split("\\s+");
    String first = s.substring(0, 1);
    if (first.toLowerCase().equals(first)) {
      if (split.length == 1) return new Func(split[0], 0);
      if (split.length == 2) return new Func(split[0], Integer.parseInt(split[1]));
      throw new IllegalArgumentException("s = " + s);
    }
    
    if (split.length == 1) return new Var(split[0]);
    if (split.length == 2) return new Power(Integer.parseInt(split[1]), new Var(split[0]));
    
    throw new IllegalArgumentException("s = " + s);
  }
  
  public static Diff diff(Form form, int n) {
    return new Diff(n, form);
  }
  
  public static Form f(String s) {
    {
      String[] split = s.split("/");
      if (split.length == 2) {
        return ConstInt.get(new BigInteger(split[0]), new BigInteger(split[1]));
      }
    }
    
    {
      int idx = s.indexOf('.');
      if (idx >= 0) return new ConstDouble(Double.parseDouble(s));
    }
    
    boolean minis = false;
    {
      if (s.startsWith("-")) {
        s = s.substring(1);
        minis = true;
      }
    }
    
    {
      char c0 = s.charAt(0);
      if ('0' <= c0 && c0 <= '9') {
        if (minis) s = "-" + s;
        return ConstInt.get(new BigInteger(s));
      }
    }
    
    return minis ? new Minis(var(s)) : var(s);
  }
  
  public static Form f(String f1, String op, String f2) {
    return f(f(f1), op, f(f2));
  }
  
  public static Form dn(int n, Form form) {
    return new Diff(n, form);
  }
  
  public static Form dn(int n, String a) {
    return new Diff(n, f(a));
  }
  
  public static Form d(Form form) {
    return dn(1, form);
  }
  
  public static Form d(String a) {
    return dn(1, a);
  }
  
  public static Form d2(Form form) {
    return dn(2, form);
  }
  
  public static Form d2(String a) {
    return dn(2, a);
  }
  
  public static Form d3(Form form) {
    return dn(3, form);
  }
  
  public static Form d3(String a) {
    return dn(3, a);
  }
  
  public static Form d4(Form form) {
    return dn(4, form);
  }
  
  public static Form d4(String a) {
    return dn(4, a);
  }
  
  public static Form f(String f1, String op, Form f2) {
    return f(f(f1), op, f2);
  }
  
  public static Form f(Form f1, String op, String f2) {
    return f(f1, op, f(f2));
  }
  
  public static Power p(Form form, int n) {
    return new Power(n, form);
  }
  
  public static Power p(String form, int n) {
    return new Power(n, f(form));
  }
  
  public static Form f(Form f1, String op, Form f2) {
    if ("+".equals(op)) return new Plus(f1, f2);
    if ("-".equals(op)) return new Minus(f1, f2);
    if ("*".equals(op)) return new Mul(f1, f2);
    if ("·".equals(op)) return new Mul(f1, f2);
    if ("/".equals(op)) return new Div(f1, f2);
    throw new IllegalArgumentException("op = " + op);
  }
  
  public static Form mul(Form first, Form second, Form... form) {
    Form ret = new Mul(first, second);
    for (Form x : form) {
      ret = new Mul(ret, x);
    }
    return ret;
  }
  
  public static Form plus(Object... oo) {
    if (oo.length == 0) throw new IllegalArgumentException();
    Form ret = null;
    for (Object o : oo) {
      if (o == null) continue;
      Form add = null;
      if (o instanceof Form) {
        add = (Form)o;
      } else if (o instanceof String) {
        add = f((String)o);
      } else throw new IllegalArgumentException("o = " + o.getClass() + ":" + o);
      
      if (ret == null) {
        ret = add;
      } else {
        ret = new Plus(ret, add);
      }
    }
    return ret;
  }
}
