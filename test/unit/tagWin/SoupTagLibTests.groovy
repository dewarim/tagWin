package tagWin



import grails.test.mixin.TestFor

/**
 * See the API for {@link grails.test.mixin.web.GroovyPageUnitTestMixin} for usage instructions
 */
@TestFor(SoupTagLib)
class SoupTagLibTests extends GroovyTestCase{

    void testStripHtml() {
        String fooBar = "Foo Bar Bazz"
        assert fooBar.length().equals(  new SoupTagLib().stripHtml([value: '<h1>Foo<i>Bar</i>Bazz</h1>'],null).toString().length())
        assert 'Foo Bar Bazz' .equals( new SoupTagLib().stripHtml([value: '<h1>Foo<i>Bar</i>Bazz</h1>'],null).toString())
    }
    
    void testStripLinks(){
        String t = "<p><a href='badLink'>link <em>text</em></a></p>"
        String stripped = "<p>link <em>text</em></p>" 
        assert stripped.equals(new SoupTagLib().stripLinks([value: t], null).toString().trim())
    }
}
