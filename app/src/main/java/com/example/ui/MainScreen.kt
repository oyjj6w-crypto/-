package com.example.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Compare
import androidx.compose.material.icons.filled.FolderSpecial
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Pin
import androidx.compose.material.icons.outlined.AutoStories
import androidx.compose.material.icons.outlined.Compare
import androidx.compose.material.icons.outlined.FolderSpecial
import androidx.compose.material.icons.outlined.Pin
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.ChineseSeal
import com.example.ui.screens.FateMetricsScreen
import com.example.ui.screens.HistoryScreen
import com.example.ui.screens.PaiPanScreen
import com.example.ui.screens.VersesScreen
import com.example.ui.theme.BronzeGold
import com.example.ui.theme.InkBlack
import com.example.ui.theme.InkGrey
import com.example.ui.theme.SealRed
import com.example.ui.theme.SealRedBg
import com.example.ui.theme.XuanPaper
import com.example.ui.theme.XuanPaperBorder
import com.example.ui.theme.XuanPaperCard

data class NavItem(
    val title: String,
    val selectedIcon: androidx.compose.ui.graphics.vector.ImageVector,
    val unselectedIcon: androidx.compose.ui.graphics.vector.ImageVector,
    val testTag: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: TiebanViewModel,
    modifier: Modifier = Modifier
) {
    val selectedTab by viewModel.selectedTab.collectAsState()
    val statusToast by viewModel.statusToast.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    // 处理安卓系统返回键 (如果在子Tab，返回排盘页)
    BackHandler(enabled = selectedTab != 0) {
        viewModel.selectTab(0)
    }

    LaunchedEffect(statusToast) {
        statusToast?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearStatusToast()
        }
    }

    val navItems = listOf(
        NavItem("排盘起数", Icons.Filled.AutoStories, Icons.Outlined.AutoStories, "tab_paipan"),
        NavItem("数理指标", Icons.Filled.Pin, Icons.Outlined.Pin, "tab_metrics"),
        NavItem("双栏断语", Icons.Filled.Compare, Icons.Outlined.Compare, "tab_verses"),
        NavItem("古籍书匣", Icons.Filled.FolderSpecial, Icons.Outlined.FolderSpecial, "tab_history")
    )

    Scaffold(
        modifier = modifier.fillMaxSize().testTag("main_screen_scaffold"),
        contentWindowInsets = WindowInsets.safeDrawing,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "铁板神数",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = InkBlack,
                            fontFamily = FontFamily.Serif,
                            letterSpacing = 2.sp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        ChineseSeal(text = "御定", isSmall = true)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = XuanPaper,
                    titleContentColor = InkBlack
                ),
                actions = {
                    Box(
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .border(1.dp, BronzeGold.copy(alpha = 0.5f))
                            .background(XuanPaperCard)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "邵子神数 · 考刻定轨",
                            fontSize = 11.sp,
                            color = BronzeGold,
                            fontFamily = FontFamily.Serif
                        )
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = XuanPaperCard,
                contentColor = InkBlack,
                tonalElevation = 6.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.navigationBars)
                    .border(width = 1.dp, color = XuanPaperBorder)
                    .testTag("bottom_navigation_bar")
            ) {
                navItems.forEachIndexed { index, item ->
                    val isSelected = selectedTab == index
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { viewModel.selectTab(index) },
                        icon = {
                            Icon(
                                imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                                contentDescription = item.title
                            )
                        },
                        label = {
                            Text(
                                text = item.title,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                fontFamily = FontFamily.Serif
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = SealRed,
                            selectedTextColor = SealRed,
                            indicatorColor = SealRedBg,
                            unselectedIconColor = InkGrey,
                            unselectedTextColor = InkGrey
                        ),
                        modifier = Modifier.testTag(item.testTag)
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(XuanPaper)
        ) {
            when (selectedTab) {
                0 -> PaiPanScreen(viewModel = viewModel)
                1 -> FateMetricsScreen(viewModel = viewModel)
                2 -> VersesScreen(viewModel = viewModel)
                3 -> HistoryScreen(viewModel = viewModel)
            }
        }
    }
}
