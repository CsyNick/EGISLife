package com.jpyu.MRTstation;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public final class XMLUtils {
	 public static String getChildStringValueForElement(final Element base, final String name) {
	      String value = null;
	      NodeList nodeList = base.getChildNodes();
	      if (nodeList.getLength() > 0) {
	          int length = nodeList.getLength();
	          for (int i = 0; i < length; i++) {
	              Node node = nodeList.item(i);

	              // Get an element, create an instance of the element
	              if (Node.ELEMENT_NODE == node.getNodeType()) {
	                  if (name.equals(node.getNodeName())) {
	                      // Get value of this child
	                      Node elNode = ((Element) node).getFirstChild();
	                      if (elNode != null) {
	                          value = elNode.getNodeValue();
	                          break;
	                      }
	                  }
	              }
	          }
	      }
	      return value;
	  }
}



