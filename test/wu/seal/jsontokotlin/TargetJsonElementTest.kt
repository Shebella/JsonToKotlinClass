package wu.seal.jsontokotlin

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Test

/**
 *
 * Created by Seal.Wu on 2018/2/6.
 */
class TargetJsonElementTest {

    val error = "UnSupport Json Format String !!"

    @Before
    fun setUp() {
        isTestModel = true
    }

    @Test
    fun getTargetJsonElementForGeneratingCode() {
        val gson = Gson()
        val text1 = """  {"UserID":11, "Name":"Truly", "Email":"zhuleipro◎hotmail.com"}"""
        val text2 = """ [
                    {"UserID":11, "Name":{"FirstName":"Truly","LastName":"Zhu"}, "Email":"zhuleipro◎hotmail.com"},
                    {"UserID":12, "Name":{"FirstName":"Jeffrey","LastName":"Richter"}, "Email":"xxx◎xxx.com"},
                    {"UserID":13, "Name":{"FirstName":"Scott","LastName":"Gu"}, "Email":"xxx2◎xxx2.com"}
                    ]"""
        val text3 = """ [] """

        val text4 = """ 1  """

        val text5 = """[1,2,3]"""

        val text6 = """["1","2","3"]"""

        val text7 = """[1,"2",true]"""


        val targetElementJson1 = getTargetElementJson(gson, text1)
        targetElementJson1.should.be.equal(gson.toJson(gson.fromJson(text1, JsonElement::class.java)))

        val targetElementJson2 = getTargetElementJson(gson, text2)
        targetElementJson2.should.be.equal(gson.toJson(gson.fromJson(text2, JsonArray::class.java)[0]))

        val targetElementJson3 = getTargetElementJson(gson, text3)
        targetElementJson3.should.be.equal(gson.toJson(Any()))

        val targetElementJson4 = getTargetElementJson(gson, text4)
        targetElementJson4.should.be.equal(error)

        val targetElementJson5 = getTargetElementJson(gson, text5)
        targetElementJson5.should.be.equal(error)

        val targetElementJson6 = getTargetElementJson(gson, text6)
        targetElementJson6.should.be.equal(error)

        val targetElementJson7 = getTargetElementJson(gson, text7)
        targetElementJson7.should.be.equal(error)

    }

    private fun getTargetElementJson(gson: Gson, text1: String): String {
        return try {
            gson.toJson(TargetJsonElement(text1).getTargetJsonElementForGeneratingCode())
        } catch(e: Exception) {
            error
        }
    }

}