package com.securis.myapplication.ui.screens

import AppTopBar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.securis.myapplication.R
import com.securis.myapplication.navigation.NavigationDestination
import com.securis.myapplication.ui.viewModels.AppViewModelProvider
import com.securis.myapplication.ui.viewModels.StatsViewModel


object BookStatsDestination : NavigationDestination {
    override val route = "book_Stats"
    override val titleRes = R.string.book_statistics_title
}

@Composable
fun StatsScreen(navigateBack: () -> Unit,read: Int, started: Int, notStarted: Int) {
    val chartData = listOf(
        "Read" to read,
        "Started" to started,
        "Not Started" to notStarted
    )

    Column(
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.padding_medium))
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        AppTopBar(title = "Book Statistics  ", onBackClick = navigateBack)

        Text(
            text = stringResource(R.string.stats_title), // You can define this in strings.xml
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground
        )

        chartData.forEach { (label, value) ->
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(id = R.dimen.padding_medium))
                ) {
                    Text(text = label, style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.padding(top = 4.dp))
                    // Bar chart bar
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(24.dp)
                                .padding(end = 8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(fraction = value.coerceAtMost(10) / 10f)
                                    .fillMaxHeight()
                                    .background(MaterialTheme.colorScheme.primary)
                            )
                        }
                        Text(text = value.toString(), fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun StatsScreenRoute(
    navigateBack: () -> Unit,
    viewModel: StatsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()

    StatsScreen(
        read = uiState.readCount,
        started = uiState.startedCount,
        notStarted = uiState.notStartedCount,
        navigateBack = navigateBack
    )
}


@Preview
@Composable
fun BookStatPreview(){
    StatsScreen(
        read = 4,
        started = 6,
        notStarted = 12,
        navigateBack = { }
    )
}