package tagWin

import org.xml.sax.XMLReader
import org.ccil.cowan.tagsoup.Parser
import org.xml.sax.InputSource

class TagWinService {

    static transactional = false

    String stripHtml(attrs) {

        if (! attrs.value || attrs.value.trim().length() == 0){
            return attrs.placeHolder ?: ''
        }
        StringBuilder result = new StringBuilder(attrs.value.length())

        XMLReader xr = new org.ccil.cowan.tagsoup.Parser()
        xr.setFeature(Parser.namespacesFeature, false);
        xr.setFeature(Parser.namespacePrefixesFeature, false);
        MySax mySax = new MySax()
        xr.setContentHandler(mySax)
        xr.setErrorHandler(mySax)
        InputSource inputSource = new InputSource(new StringReader(attrs.value))
        xr.parse(inputSource)
        if (attrs.maxSize){
            def maxSize = Integer.parseInt(attrs.maxSize)
            def content = mySax.content.toString()
            if (content.length() > maxSize){
                result.append(content.substring(0, maxSize))
                result.append(attrs.placeHolder ?: '')
            }
            else{
                result.append(content.substring(0, content.length() ))
            }
        }
        else{
            result.append(mySax.content.toString())
        }
        return result.toString()
    }
}
