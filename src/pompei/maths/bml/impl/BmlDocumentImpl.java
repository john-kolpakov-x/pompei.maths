package pompei.maths.bml.impl;

import pompei.maths.bml.iface.BmlDocument;
import pompei.maths.bml.iface.BmlTag;
import pompei.maths.bml.iface.as.AsBytes;

import java.util.ArrayList;

public class BmlDocumentImpl extends ArrayList<BmlTag> implements BmlDocument {
  @Override
  public BmlTag createTag() {
    return null;
  }

  @Override
  public AsBytes content() {
    return null;
  }
}
