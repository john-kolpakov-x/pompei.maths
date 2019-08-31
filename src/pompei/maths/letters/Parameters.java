package pompei.maths.letters;

import pompei.maths.letters.core.DoubleValue;
import pompei.maths.letters.core.LabelSplitterEditor;

public interface Parameters {

  @LabelSplitterEditor("v1")
  DoubleValue width();

  @LabelSplitterEditor("v2")
  DoubleValue height();

  @LabelSplitterEditor("v3")
  DoubleValue topRadius();

}
