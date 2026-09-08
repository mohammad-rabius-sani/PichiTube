package com.pichitube.app.feature.settings.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pichitube.app.core.data.prefs.SUPPORTED_REGIONS
import com.pichitube.app.core.data.prefs.ThemeMode
import com.pichitube.app.core.data.prefs.VideoQuality
import com.pichitube.app.feature.settings.SettingsViewModel
import com.pichitube.app.ui.components.UpdateModal
import com.pichitube.app.ui.components.WhatsNewModal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val prefs by viewModel.prefs.collectAsStateWithLifecycle()
    val updateState by viewModel.updateState.collectAsStateWithLifecycle()

    var showThemePicker by remember { mutableStateOf(false) }
    var showQualityPicker by remember { mutableStateOf(false) }
    var showRegionPicker by remember { mutableStateOf(false) }
    var showClearHistoryDialog by remember { mutableStateOf(false) }
    var showClearSearchDialog by remember { mutableStateOf(false) }
    var showWhatsNewDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back") }
                },
                title = { Text("Settings", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background),
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { innerPadding ->
        LazyColumn(
            contentPadding = PaddingValues(
                top = innerPadding.calculateTopPadding() + 8.dp,
                bottom = 120.dp,
                start = 16.dp,
                end = 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // ─── 1. Appearance ──────────────────────────────────────────────────────────
            item {
                SettingsCardGroup(title = "Appearance", icon = Icons.Default.Palette) {
                    SettingsRowItem(
                        icon = Icons.Default.DarkMode,
                        title = "Theme",
                        subtitle = when (prefs.themeMode) {
                            ThemeMode.AMOLED -> "AMOLED Black (Battery Saver)"
                            ThemeMode.DARK -> "Dark Theme"
                            ThemeMode.LIGHT -> "Light Theme"
                        },
                        onClick = { showThemePicker = true }
                    )
                }
            }

            // ─── 2. Video & Audio Playback ─────────────────────────────────────────────
            item {
                SettingsCardGroup(title = "Playback & Video", icon = Icons.Default.PlayCircle) {
                    SettingsRowItem(
                        icon = Icons.Default.Hd,
                        title = "Default Video Quality",
                        subtitle = when (prefs.defaultQuality) {
                            VideoQuality.AUTO -> "Auto (Adaptive)"
                            VideoQuality.Q2160P -> "2160p (4K Ultra HD)"
                            VideoQuality.Q1440P -> "1440p (2K Quad HD)"
                            VideoQuality.Q1080P -> "1080p Full HD"
                            VideoQuality.Q720P -> "720p HD"
                            VideoQuality.Q480P -> "480p"
                            VideoQuality.Q360P -> "360p"
                        },
                        onClick = { showQualityPicker = true }
                    )
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))
                    SettingsSwitchRow(
                        icon = Icons.Default.Memory,
                        title = "Hardware Decoder",
                        subtitle = "Hardware-accelerated rendering for smooth 60fps & 4K",
                        checked = prefs.hardwareDecoder,
                        onCheckedChange = viewModel::setHardwareDecoder
                    )
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))
                    SettingsRowItem(
                        icon = Icons.Default.PictureInPicture,
                        title = "Mini-Player & Background Audio",
                        subtitle = "Continues playing audio when minimized or screen is locked",
                        onClick = {}
                    )
                }
            }

            // ─── 3. Content & Region ───────────────────────────────────────────────────
            item {
                SettingsCardGroup(title = "Content & Region", icon = Icons.Default.Public) {
                    val currentRegionData = SUPPORTED_REGIONS.find { it.code.equals(prefs.contentRegion, ignoreCase = true) }
                    val regionLabel = if (currentRegionData != null) "${currentRegionData.flag} ${currentRegionData.name}" else prefs.contentRegion
                    SettingsRowItem(
                        icon = Icons.Default.Language,
                        title = "Content Region",
                        subtitle = regionLabel,
                        onClick = { showRegionPicker = true }
                    )
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))
                    SettingsSwitchRow(
                        icon = Icons.Default.Slideshow,
                        title = "Show Shorts Shelf",
                        subtitle = "Display YouTube Shorts shelf in home feed",
                        checked = prefs.showShorts,
                        onCheckedChange = viewModel::setShowShorts
                    )
                }
            }

            // ─── 4. Ad-Free & SponsorBlock ─────────────────────────────────────────────
            item {
                SettingsCardGroup(title = "Ad-Free & SponsorBlock", icon = Icons.Default.Shield) {
                    SettingsSwitchRow(
                        icon = Icons.Default.FastForward,
                        title = "SponsorBlock",
                        subtitle = "Automatically skips sponsors & intro segments",
                        checked = prefs.sponsorBlockEnabled,
                        onCheckedChange = viewModel::setSponsorBlock
                    )
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))
                    SettingsRowItem(
                        icon = Icons.Default.Verified,
                        title = "Ad-Free Experience",
                        subtitle = "All video ads, banners, and interruptions permanently blocked",
                        onClick = {},
                        iconTint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            // ─── 5. Updates & Releases ─────────────────────────────────────────────────
            item {
                SettingsCardGroup(title = "Updates & Releases", icon = Icons.Default.SystemUpdate) {
                    SettingsRowItem(
                        icon = Icons.Default.Refresh,
                        title = "Check for Updates",
                        subtitle = "Check online for latest PichiTube release",
                        onClick = viewModel::checkForUpdates
                    )
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))
                    SettingsRowItem(
                        icon = Icons.Default.Celebration,
                        title = "What's New in v1.0.0",
                        subtitle = "View changelog and latest additions",
                        onClick = { showWhatsNewDialog = true }
                    )
                }
            }

            // ─── 6. Privacy & Data ─────────────────────────────────────────────────────
            item {
                SettingsCardGroup(title = "Privacy & Local Data", icon = Icons.Default.Lock) {
                    SettingsRowItem(
                        icon = Icons.Default.History,
                        title = "Clear Watch History",
                        subtitle = "Delete all locally stored video watch history",
                        onClick = { showClearHistoryDialog = true },
                        iconTint = MaterialTheme.colorScheme.error
                    )
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))
                    SettingsRowItem(
                        icon = Icons.Default.ManageSearch,
                        title = "Clear Search History",
                        subtitle = "Remove all recent search suggestions",
                        onClick = { showClearSearchDialog = true },
                        iconTint = MaterialTheme.colorScheme.error
                    )
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f))
                    SettingsRowItem(
                        icon = Icons.Default.Security,
                        title = "100% Privacy Guarantee",
                        subtitle = "Zero tracking • No account needed • All data stays on device",
                        onClick = {},
                        iconTint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            // ─── 7. About & Author ─────────────────────────────────────────────────────
            item {
                SettingsCardGroup(title = "About & Author", icon = Icons.Default.Info) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(46.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Default.PlayArrow,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                            Spacer(Modifier.width(14.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    "PichiTube",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    "Version 1.0.0 • Pure Ad-Free",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        Spacer(Modifier.height(14.dp))
                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.25f))
                        Spacer(Modifier.height(14.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF222222)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Default.Person,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                            Spacer(Modifier.width(14.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    "Rabius Sani",
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    "Owner & CEO, PichiPie Lab",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }

                            // Quick Action Icons
                            IconButton(
                                onClick = {
                                    runCatching {
                                        context.startActivity(
                                            Intent(Intent.ACTION_VIEW, Uri.parse("https://rabius-sani-portfolio.vercel.app/"))
                                        )
                                    }
                                }
                            ) {
                                Icon(
                                    Icons.Default.Language,
                                    contentDescription = "Portfolio",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                            IconButton(
                                onClick = {
                                    runCatching {
                                        context.startActivity(
                                            Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:mohammad.rabius.sanii@gmail.com"))
                                        )
                                    }
                                }
                            ) {
                                Icon(
                                    Icons.Default.Email,
                                    contentDescription = "Email",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // In-App Update Modal is hosted globally in MainActivity

    // What's New Modal
    if (showWhatsNewDialog) {
        WhatsNewModal(onDismiss = { showWhatsNewDialog = false })
    }

    // Theme picker dialog
    if (showThemePicker) {
        AlertDialog(
            onDismissRequest = { showThemePicker = false },
            title = { Text("Choose Theme", fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    ThemeMode.entries.forEach { mode ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.setThemeMode(mode)
                                    showThemePicker = false
                                }
                                .padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            RadioButton(
                                selected = prefs.themeMode == mode,
                                onClick = {
                                    viewModel.setThemeMode(mode)
                                    showThemePicker = false
                                },
                                colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary),
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(
                                when (mode) {
                                    ThemeMode.AMOLED -> "AMOLED Black (Pure Black)"
                                    ThemeMode.DARK -> "Dark Theme"
                                    ThemeMode.LIGHT -> "Light Theme"
                                },
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            },
            confirmButton = { TextButton(onClick = { showThemePicker = false }) { Text("Cancel") } }
        )
    }

    // Quality picker dialog
    if (showQualityPicker) {
        AlertDialog(
            onDismissRequest = { showQualityPicker = false },
            title = { Text("Default Video Quality", fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    VideoQuality.entries.forEach { quality ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.setDefaultQuality(quality)
                                    showQualityPicker = false
                                }
                                .padding(vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            RadioButton(
                                selected = prefs.defaultQuality == quality,
                                onClick = {
                                    viewModel.setDefaultQuality(quality)
                                    showQualityPicker = false
                                },
                                colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary),
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(
                                when (quality) {
                                    VideoQuality.AUTO -> "Auto (Adaptive)"
                                    VideoQuality.Q2160P -> "2160p (4K Ultra HD)"
                                    VideoQuality.Q1440P -> "1440p (2K Quad HD)"
                                    VideoQuality.Q1080P -> "1080p Full HD"
                                    VideoQuality.Q720P -> "720p HD"
                                    VideoQuality.Q480P -> "480p"
                                    VideoQuality.Q360P -> "360p"
                                },
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            },
            confirmButton = { TextButton(onClick = { showQualityPicker = false }) { Text("Cancel") } }
        )
    }

    // Region picker dialog
    if (showRegionPicker) {
        AlertDialog(
            onDismissRequest = { showRegionPicker = false },
            title = { Text("Content Region", fontWeight = FontWeight.Bold) },
            text = {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 380.dp)
                ) {
                    items(SUPPORTED_REGIONS) { region ->
                        val isSelected = region.code.equals(prefs.contentRegion, ignoreCase = true)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.setContentRegion(region.code)
                                    showRegionPicker = false
                                }
                                .padding(vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            RadioButton(
                                selected = isSelected,
                                onClick = {
                                    viewModel.setContentRegion(region.code)
                                    showRegionPicker = false
                                },
                                colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary),
                            )
                            Spacer(Modifier.width(10.dp))
                            Text(
                                text = "${region.flag}  ${region.name}",
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            },
            confirmButton = { TextButton(onClick = { showRegionPicker = false }) { Text("Cancel") } }
        )
    }

    // Clear Watch History confirmation
    if (showClearHistoryDialog) {
        AlertDialog(
            onDismissRequest = { showClearHistoryDialog = false },
            title = { Text("Clear Watch History?") },
            text = { Text("This will permanently clear your watched videos list from your device.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.clearHistory()
                        showClearHistoryDialog = false
                    }
                ) {
                    Text("Clear", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showClearHistoryDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    // Clear Search History confirmation
    if (showClearSearchDialog) {
        AlertDialog(
            onDismissRequest = { showClearSearchDialog = false },
            title = { Text("Clear Search History?") },
            text = { Text("This will remove all recent searches from the search page.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.clearSearchHistory()
                        showClearSearchDialog = false
                    }
                ) {
                    Text("Clear", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showClearSearchDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
private fun SettingsCardGroup(
    title: String,
    icon: ImageVector,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(18.dp)
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                content()
            }
        }
    }
}

@Composable
private fun SettingsRowItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    iconTint: Color = MaterialTheme.colorScheme.onSurfaceVariant,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(24.dp))
        Spacer(Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Icon(
            Icons.Default.ChevronRight,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
private fun SettingsSwitchRow(
    icon: ImageVector,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!checked) }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(24.dp)
        )
        Spacer(Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.primary,
                checkedTrackColor = MaterialTheme.colorScheme.primaryContainer
            )
        )
    }
}
