package xyz.teamgravity.shoppinglisttest.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import xyz.teamgravity.shoppinglisttest.getOrAwaitValue
import xyz.teamgravity.shoppinglisttest.model.ShoppingItemModel

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class ShoppingDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: MyDatabase
    private lateinit var dao: ShoppingDao

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), MyDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.dao()
    }

    @After
    fun teardown() {
        db.close()
    }

    @Test
    fun insertShoppingItem() = runBlockingTest {
        val shoppingItem = ShoppingItemModel("name", 5, 1F, "url", 1)
        dao.insert(shoppingItem)

        val allShoppingItems = dao.getShoppingItems().getOrAwaitValue()

        assertThat(allShoppingItems).contains(shoppingItem)
    }

    @Test
    fun deleteShoppingItem() = runBlockingTest {
        val shoppingItem = ShoppingItemModel("name", 5, 1F, "url", 1)
        dao.insert(shoppingItem)

        dao.delete(shoppingItem)

        val allShoppingItems = dao.getShoppingItems().getOrAwaitValue()

        assertThat(allShoppingItems).doesNotContain(allShoppingItems)
    }

    @Test
    fun getAllItemPrice() = runBlockingTest {
        val shoppingItem1 = ShoppingItemModel("name", 5, 1F, "url", 1)
        val shoppingItem2 = ShoppingItemModel("name", 5, 5F, "url", 2)
        val shoppingItem3 = ShoppingItemModel("name", 5, 10F, "url", 3)

        dao.insert(shoppingItem1)
        dao.insert(shoppingItem2)
        dao.insert(shoppingItem3)

        val totalFee = dao.getAllItemPrice().getOrAwaitValue()

        assertThat(totalFee).isEqualTo(80F)
    }
}