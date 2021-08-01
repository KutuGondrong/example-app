package com.kutugondrong.exampleapp

import com.kutugondrong.jsonkg.JsonKG
import com.kutugondrong.jsonkg.SpecialSerializedName
import org.junit.Assert
import org.junit.Test

/**
 * Unit test for JsonParser
 * @see JsonKG
 */

class JsonParserTest {

    private val exampleJsonObject = "{\n" +
            "\t\"id\": 206,\n" +
            "\t\"name\": \"Hedy Simamora\"\n" +
            "}"

    private val exampleJsonArray =
        "[{ \"id\": 206, \"name\": \"Hedy Simamora\" }, { \"id\": 201, \"name\": \"Hedy Simamora\" }]"

    class User {
        var id: Int? = null
        var name: String? = null
    }

    data class A(
        val a: String
    )
    data class B(
        val listA: MutableList<A>
    )
    data class C(
        @SpecialSerializedName("c1", "c2")
        val listB: ArrayList<B>
    )
    data class D(
       val c: C
    )

    @Test
    fun `Test Success JsonParser from json to object`() {
        val jsonParser = JsonKG()
        val result = jsonParser.fromJson(exampleJsonObject, User::class) as User
        Assert.assertEquals(result.id, 206)
        Assert.assertEquals(result.name, "Hedy Simamora")
    }

    @Test
    fun `Test Failed JsonParser from json to object`() {
        val jsonParser = JsonKG()
        val result = jsonParser.fromJson(
            "{}",
            User::class,
        ) as User
        Assert.assertNotEquals(result.id, 206)
        Assert.assertNotEquals(result.name, "Hedy Simamora")
    }

    @Test
    fun `Test Success JsonParser from json to Array`() {
        val jsonParser = JsonKG()
        val result = jsonParser.fromJson(exampleJsonArray, User::class, true) as ArrayList<User>
        Assert.assertEquals(result.size > 0, true)
    }

    @Test
    fun `Test Failed JsonParser from json to Array`() {
        val jsonParser = JsonKG()
        val result = jsonParser.fromJson(
            "[]",
            User::class, true
        ) as ArrayList<User>
        Assert.assertNotEquals(result.size > 0, true)
    }

    @Test
    fun `Test List in list jsonkg`() {
        val a = A("Coba")
        val b: B = B(arrayListOf(a,a,a,a,a,a,a))
        val c: C = C(arrayListOf(b,b,b,b,b,b))
        val d = D(c)
        val test = JsonKG()
        var v = "["+test.toJson(d)+"]"
        val z: MutableList<D> = test.fromJson(v, d::class, true)
        println(z.toString())
    }

}