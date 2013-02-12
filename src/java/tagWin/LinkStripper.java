package tagWin;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 */
public class LinkStripper extends DefaultHandler {

    StringBuilder content = new StringBuilder();

    public LinkStripper() {
        super();
    }

    @Override
    public void startElement(String uri,
                             String localName,
                             String qName,
                             Attributes attributes){
        if(qName.matches("^(?i:img|a|html|body|script)$")){
            return;
        }
        content.append("<");
        content.append(qName);
        content.append(">");        
    }
    
    @Override
    public void endElement(String uri,
                           String localName,
                           String qName){
        if(qName.matches("^(?i:img|a|html|body|script)$")){
            return;
        }
        content.append("</");
        content.append(qName);
        content.append(">");
    }
    
    @Override
    public void characters(char ch[], int start, int length) {
        if(content.length() > 0){
            // separate individual strings by whitespace.
//            content.append(" ");
        }

        for(int i = start; i< start+length;i++){
            content.append(ch[i]);
        }
    }

    public StringBuilder getContent() {
        return content;
    }

    public void setContent(StringBuilder content) {
        this.content = content;
    }
}
