package pompei.maths.bml.impl;

import org.testng.annotations.Test;
import pompei.maths.bml.iface.BmlTag;

import static org.fest.assertions.Assertions.assertThat;

public class BmlDocumentImplTest {

  @Test
  public void empty() {

    BmlDocumentImpl document = new BmlDocumentImpl();

    BmlTag tag = document.createTag();

    document.add(tag);

    assertThat(document).hasSize(1);

  }

}
