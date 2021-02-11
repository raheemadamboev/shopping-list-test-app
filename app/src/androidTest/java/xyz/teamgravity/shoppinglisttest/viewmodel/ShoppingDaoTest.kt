package xyz.teamgravity.shoppinglisttest.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import xyz.teamgravity.shoppinglisttest.getOrAwaitValue
import xyz.teamgravity.shoppinglisttest.model.ShoppingItemModel
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class ShoppingDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("memory_database")
    lateinit var db: MyDatabase

    private lateinit var dao: ShoppingDao

    @Before
    fun setup() {
        hiltRule.inject()
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