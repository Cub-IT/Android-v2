package com.example.cubit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.core.presentation.theme.CubITTheme
import com.example.core.util.ViewModelCreator
import com.example.core.util.ViewModelFactory
import com.example.feature_auth.presentation.sign_in.SignInViewModel
import com.example.feature_auth.presentation.sign_in.SingInScreen
import com.example.feature_group.presentation.common.item.GroupItem
import com.example.feature_group.presentation.group.composable.GroupHeaderCard
import com.example.feature_group.presentation.group.composable.Task
import com.example.feature_group.presentation.group.item.PostItem
import com.example.feature_group.presentation.group_list.GroupListScreen
import com.example.feature_group.presentation.group_list.GroupListViewModel
import com.example.feature_group.presentation.group_list.composable.GroupCard
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    inline fun <reified VM : ViewModel> ComponentActivity.viewModelCreator(
        noinline creator: ViewModelCreator<VM>
    ): Lazy<VM> {
        return viewModels { ViewModelFactory(creator) }
    }

    @Inject
    lateinit var factory: GroupListViewModel.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CubITTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //Greeting("Android")

                    val viewModel by viewModelCreator {
                        factory.create({})
                    }
                    //SingInScreen(viewModel = viewModel)
                    GroupListScreen(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CubITTheme {
        Greeting("Android")
    }
}

