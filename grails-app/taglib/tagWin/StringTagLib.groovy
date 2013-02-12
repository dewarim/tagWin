package tagWin

/**
 *
 * Library for string manipulation tags.
 */
class StringTagLib {

    /**
     * Split a string (preserving words separated by whitespace) 
     * into lines of at most maxLength chars, separated by br-tags
     * @attr addSpaces = if set, add spaces to "_-."
     * @attr maxLength = maximum length of a line. Defaults to 30
     */
    def wrapStringHtml = {attrs, body ->
        def maxLength = Integer.parseInt(attrs.maxLength ?: '30')
        def myStr = body()
        if (myStr.length() <= maxLength) {
            out << myStr
            return
        }
        if(attrs.addSpaces){
            myStr = myStr.replaceAll('([-._])+','$1 ')
        }
        def outStr = myStr.tokenize(' ')*.
                toList()*.
                collate(maxLength)*.
                collect{it.join('')}.
                flatten().
                join(' ')
        out << outStr        
    }

}
