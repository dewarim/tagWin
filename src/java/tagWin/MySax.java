package tagWin;

import org.xml.sax.helpers.DefaultHandler;

/**
 *
 */
public class MySax extends DefaultHandler {

    StringBuilder content = new StringBuilder();

    public MySax() {
        super();
    }

    public void characters(char ch[], int start, int length) {
        if(content.length() > 0){
            // separate individual strings by whitespace.
            content.append(" ");
        }

        for(int i = start; i< start+length;i++){
            content.append(ch[i]);
        }

//        System.out.print("Characters:    \"");
//        for (int i = start; i < start + length; i++) {
//            switch (ch[i]) {
//                case '\\':
//                    System.out.print("\\\\");
//                    break;
//                case '"':
//                    System.out.print("\\\"");
//                    break;
//                case '\n':
//                    System.out.print("\\n");
//                    break;
//                case '\r':
//                    System.out.print("\\r");
//                    break;
//                case '\t':
//                    System.out.print("\\t");
//                    break;
//                default:
//                    System.out.print(ch[i]);
//                    break;
//            }
//        }
//        System.out.print("\"\n");
    }

    public StringBuilder getContent() {
        return content;
    }

    public void setContent(StringBuilder content) {
        this.content = content;
    }
}
