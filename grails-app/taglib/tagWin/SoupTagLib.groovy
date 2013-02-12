package tagWin

import org.ccil.cowan.tagsoup.Parser
import org.xml.sax.InputSource
import org.xml.sax.XMLReader

class SoupTagLib {

    def tagWinService // TODO: use tagWinService to strip out HTML. check how this works with "out" var.

    /**
     * Parse an HTML string provided in the value attribute of the tag and return
     * the text content separated by whitespace as a String.
     * @attr value REQUIRED the string which should be stripped.
     * @attr maxSize the maximum allowed length of the returned String
     * @attr placeHolder an optional placeholder (like '...') at the end of the String if maxSize
     * is less than length of value or if no value exists. This way you can always use the result
     * of this tag for the text of a link.
     */
    def stripHtml = {attrs, body ->
        if (! attrs.value || attrs.value.trim().length() == 0){
            out << attrs.placeHolder ?: ''
            return
        }
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
                out << content.substring(0, maxSize)
                out << attrs.placeHolder ?: ''
            }
            else{
                out << content.substring(0, content.length() )
            }
        }
        else{
            out << mySax.content.toString()
        }
    }
    
    /**
     * Parse an HTML string provided in the value attribute of the tag and strip any links from the text,
     * replacing them with their text content.
     * @attr value REQUIRED the string which should be stripped.
     * @attr minSize REQUIRED the minimum length of the returned String
     * @attr placeHolder an optional placeholder (like '...') at the end of the String if minSize
     * is larger than length of value or if no value exists. This way you can always use the result
     * of this tag for the text of a link.
     */
    def stripLinks = {attrs, body ->
        if (! attrs.value || attrs.value.trim().length() == 0){
            out << attrs.placeHolder ?: ''
            return
        }
        XMLReader xr = new org.ccil.cowan.tagsoup.Parser()
        xr.setFeature(Parser.namespacesFeature, false);
        xr.setFeature(Parser.namespacePrefixesFeature, false);
        LinkStripper mySax = new LinkStripper()
        xr.setContentHandler(mySax)
        xr.setErrorHandler(mySax)
        InputSource inputSource = new InputSource(new StringReader(attrs.value))
        xr.parse(inputSource)
        if (attrs.minSize){
            def minSize = Integer.parseInt(attrs.minSize)
            def content = mySax.content.toString()
            if (content.length() < minSize){
                out << content.substring(0, minSize)
                out << attrs.placeHolder ?: ''
            }
            else{
                out << content
            }
        }
        else{
            out << mySax.content.toString()
        }
    }

    /**
     * Convert an image file into a data URI and generate an image tag for it.
     * @attr contentType REQUIRED the contentType, for example "image/jpeg"
     * @attr id the ide of the image tag
     * @attr alt the image alt text
     * @attr height the image height, default: 100%
     * @attr width the image width, default: 100%
     * @attr cssClass the css classes of the image tag
     *
     */
    def dataImage = {attrs, body ->
        def contentType = attrs.contentType
        File imageFile = new File(attrs.filename)
        def imageBytes = imageFile.bytes
        def altText = attrs.alt
        def height = attrs.height ?: "100%"
        def width = attrs.width ?: "100%"
        def cssClass = attrs.cssClass ?: ""
        def id = attrs.id ? "id=\"${id}\"" : ""
        out << """<img src="data:${contentType};base64,${imageBytes.encodeAsBase64()}"
        alt="${altText}" height="${height}" width="${width}" class="${cssClass}" ${id} />""".toString()
    }


    /**
     * Shorten a string provided in the value attribute of the tag.
     * @attr value REQUIRED the string which should be stripped.
     * @attr maxSize the maximum allowed length of the returned String
     * @attr placeHolder an optional placeholder (like '...') at the end of the String if maxSize
     * is less than length of value or if no value exists. This way you can always use the result
     * of this tag for the text of a link.
     */
    def shorten = {attrs, body ->
        if (! attrs.value || attrs.value.trim().length() == 0){
            out << attrs.placeHolder ?: ''
            return
        }
        if (attrs.maxSize){
            def maxSize = Integer.parseInt(attrs.maxSize)
            def content = attrs.value
            if (content.length() > maxSize){
                out << content.substring(0, maxSize)
                out << attrs.placeHolder ?: ''
            }
            else{
                out << content.substring(0, content.length() )
            }
        }
        else{
            out << attrs.value
        }
    }
}
