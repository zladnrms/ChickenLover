package io.defy.chicken.chickenlover

import io.reactivex.Observable
import io.reactivex.subjects.AsyncSubject
import io.reactivex.subjects.PublishSubject
import org.junit.Test

import org.junit.Assert.*
import java.lang.Thread.sleep
import java.util.concurrent.TimeUnit

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
        //1초 간격으로 배출시킨다.
        val source1 = Observable.interval( 1, TimeUnit.SECONDS).map{v -> "dd" + (v + 1) + " seconds"};
// 0.3 초 간격으로 배출시킨다.
        val source2 = Observable.interval(300 , TimeUnit.MILLISECONDS).map{v -> "dd" + (v + 1) + " milliseconds"};

        val a = Observable.create()


//선언
        val subject = PublishSubject.create<String>()

//값 출력
        subject.subscribe(System.out::println)

        source1.subscribe( subject );
        source2.subscribe( subject );

        sleep(3000); //3초동안 유지

    }
}
