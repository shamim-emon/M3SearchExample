package com.emon.m3searchexample

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun LandingScreen() {
    val viewModel: LandingViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            CustomTopBar(
                selectedTab = state.selectedTab,
                isSearchVisible = state.isSearchBarVisible,
                searchQuery = state.searchQuery,
                isSearchBarActive = state.isSearchBarActive,
                onSearchQueryUpdate = { viewModel.onEvent(LandingEvent.OnSearchQueryUpdate(it)) },
                toggleSearchBarActive = { viewModel.onEvent(LandingEvent.ToggleSearchBarActive(it)) },
                onTabSelect = { viewModel.onEvent(LandingEvent.OnTabSelect(it)) },
                onSearchClick = { viewModel.onEvent(LandingEvent.OnSearchClick) },
                onSearchDismiss = { viewModel.onEvent(LandingEvent.OnSearchDismiss) }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // Illustration
            Image(
                painter = painterResource(id = R.drawable.people),
                contentDescription = "People Illustration",
                modifier = Modifier.size(120.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Search Prompt
            Text(
                text = "Search for an Associate",
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Composable
fun ToggleButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
    TextButton(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.textButtonColors(
            contentColor = if (isSelected) Color.White else Color.Black
        ),
        modifier = Modifier
            .background(
                if (isSelected) Color(0xFF0D3B66) else Color.White,
                RoundedCornerShape(8.dp)
            )
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp)
    ) {
        Text(text, fontWeight = FontWeight.Bold)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(
    modifier: Modifier = Modifier,
    selectedTab: String,
    searchQuery: String,
    isSearchBarActive: Boolean,
    isSearchVisible: Boolean,
    onSearchClick: () -> Unit,
    onSearchDismiss: () -> Unit,
    onSearchQueryUpdate: (String) -> Unit,
    onTabSelect: (String) -> Unit,
    toggleSearchBarActive: (Boolean) -> Unit,
) {
    val searchPlaceHolderText by remember(isSearchBarActive) {
        if (isSearchBarActive) mutableStateOf("Search") else mutableStateOf(
            "Tap to begin Search"
        )
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(WindowInsets.statusBars.asPaddingValues())
            .background(Color(0xFF0D3B66))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            if (!isSearchVisible) {
                IconButton(
                    onClick = { /* Handle back action */ },
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "People",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                )


                Spacer(modifier = Modifier.weight(1f))

                IconButton(
                    onClick = onSearchClick,
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Color.White
                    )
                }
            } else {
                SearchBar(
                    query = searchQuery,
                    onQueryChange = onSearchQueryUpdate,
                    onSearch = { toggleSearchBarActive.invoke(false) },
                    active = isSearchBarActive,
                    onActiveChange = toggleSearchBarActive,
                    placeholder = { Text(searchPlaceHolderText) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                    trailingIcon = {
                        IconButton(onClick = {
                            onSearchQueryUpdate("")
                            toggleSearchBarActive(false)
                            onSearchDismiss()
                        }
                        ) {
                            Icon(Icons.Default.Close, contentDescription = "Clear")
                        }
                    }
                ) {
                    // Show search results
                    if (searchQuery.isNotEmpty()) {
                        LazyColumn {
                            items(20) { index ->
                                ListItem(
                                    headlineContent = { Text("Result $index for \"$searchQuery\"") },
                                    supportingContent = { Text("Description of result $index") },
                                    modifier = Modifier.clickable { toggleSearchBarActive(false) }
                                )
                            }
                        }
                    }
                }
            }
        }

        if (!isSearchBarActive) {
            Row(
                modifier = Modifier
                    .wrapContentHeight()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                ToggleButton(
                    "Recent",
                    isSelected = selectedTab == "Recent",
                    onClick = { onTabSelect.invoke("Recent") })
                ToggleButton(
                    "Followed",
                    isSelected = selectedTab == "Followed",
                    onClick = { onTabSelect.invoke("Followed") })
            }
        }

    }

}

@Preview
@Composable
fun PreviewTopBar() {
    CustomTopBar(
        selectedTab = "Recent",
        isSearchVisible = false,
        isSearchBarActive = false,
        searchQuery = "",
        onTabSelect = {},
        onSearchDismiss = {},
        onSearchClick = {},
        onSearchQueryUpdate = {},
        toggleSearchBarActive = {},
    )
}

