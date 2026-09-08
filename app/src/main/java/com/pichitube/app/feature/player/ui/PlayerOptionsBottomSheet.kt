package com.pichitube.app.feature.player.ui

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pichitube.app.core.network.Subtitle
import com.pichitube.app.core.network.SubtitleTrack

enum class PlayerOptionsPage {
    MAIN,
    QUALITY,
    AUDIO_TRACK,
    CAPTIONS,
    SPEED,
    SLEEP_TIMER
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerOptionsBottomSheet(
    selectedQuality: String,
    availableQualities: List<String>,
    onSelectQuality: (String) -> Unit,
    selectedAudioLanguage: String,
    availableAudioLanguages: List<String>,
    onSelectAudioLanguage: (String) -> Unit,
    selectedSubtitle: Subtitle?,
    availableSubtitles: List<Subtitle>,
    onSelectSubtitle: (Subtitle?) -> Unit,
    playbackSpeed: Float,
    onSelectSpeed: (Float) -> Unit,
    isLooping: Boolean,
    onToggleLoop: () -> Unit,
    sleepTimerMinutes: Int?,
    onSelectSleepTimer: (Int?) -> Unit,
    sponsorBlockEnabled: Boolean,
    onToggleSponsorBlock: () -> Unit,
    resolvedQuality: String = "",
    onDismiss: () -> Unit,
) {
    var currentPage by remember { mutableStateOf(PlayerOptionsPage.MAIN) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        AnimatedContent(
            targetState = currentPage,
            transitionSpec = {
                if (targetState == PlayerOptionsPage.MAIN) {
                    slideInHorizontally { -it } togetherWith slideOutHorizontally { it }
                } else {
                    slideInHorizontally { it } togetherWith slideOutHorizontally { -it }
                }
            },
            label = "OptionsSheetNav"
        ) { page ->
            when (page) {
                PlayerOptionsPage.MAIN -> {
                    MainOptionsList(
                        selectedQuality = selectedQuality,
                        resolvedQuality = resolvedQuality,
                        selectedAudioLanguage = selectedAudioLanguage,
                        selectedSubtitleName = selectedSubtitle?.name ?: "Off",
                        playbackSpeed = playbackSpeed,
                        isLooping = isLooping,
                        sleepTimerMinutes = sleepTimerMinutes,
                        sponsorBlockEnabled = sponsorBlockEnabled,
                        onNavigate = { currentPage = it },
                        onToggleLoop = onToggleLoop,
                        onToggleSponsorBlock = onToggleSponsorBlock,
                    )
                }
                PlayerOptionsPage.QUALITY -> {
                    SubMenuSheet(
                        title = "Quality",
                        onBack = { currentPage = PlayerOptionsPage.MAIN },
                        items = availableQualities.ifEmpty { listOf("Auto", "1080p", "720p", "480p", "360p", "Audio Only") },
                        selectedItem = selectedQuality,
                        resolvedQuality = resolvedQuality,
                        onSelect = {
                            onSelectQuality(it)
                            onDismiss()
                        }
                    )
                }
                PlayerOptionsPage.AUDIO_TRACK -> {
                    SubMenuSheet(
                        title = "Audio Track",
                        onBack = { currentPage = PlayerOptionsPage.MAIN },
                        items = availableAudioLanguages.ifEmpty { listOf("Original", "Bangla", "Hindi", "English") },
                        selectedItem = selectedAudioLanguage,
                        onSelect = {
                            onSelectAudioLanguage(it)
                            onDismiss()
                        }
                    )
                }
                PlayerOptionsPage.CAPTIONS -> {
                    CaptionsSubMenuSheet(
                        availableSubtitles = availableSubtitles,
                        selectedSubtitle = selectedSubtitle,
                        onBack = { currentPage = PlayerOptionsPage.MAIN },
                        onSelect = {
                            onSelectSubtitle(it)
                            onDismiss()
                        }
                    )
                }
                PlayerOptionsPage.SPEED -> {
                    SpeedSubMenuSheet(
                        currentSpeed = playbackSpeed,
                        onBack = { currentPage = PlayerOptionsPage.MAIN },
                        onSelect = {
                            onSelectSpeed(it)
                            onDismiss()
                        }
                    )
                }
                PlayerOptionsPage.SLEEP_TIMER -> {
                    SleepTimerSubMenuSheet(
                        currentMinutes = sleepTimerMinutes,
                        onBack = { currentPage = PlayerOptionsPage.MAIN },
                        onSelect = {
                            onSelectSleepTimer(it)
                            onDismiss()
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun MainOptionsList(
    selectedQuality: String,
    resolvedQuality: String = "",
    selectedAudioLanguage: String,
    selectedSubtitleName: String,
    playbackSpeed: Float,
    isLooping: Boolean,
    sleepTimerMinutes: Int?,
    sponsorBlockEnabled: Boolean,
    onNavigate: (PlayerOptionsPage) -> Unit,
    onToggleLoop: () -> Unit,
    onToggleSponsorBlock: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 32.dp)
    ) {
        Text(
            text = "Playback Settings",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
        )
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))

        // Quality
        val displayQuality = if (selectedQuality.equals("Auto", ignoreCase = true) && resolvedQuality.isNotBlank()) {
            "Auto ($resolvedQuality)"
        } else {
            selectedQuality
        }
        OptionRow(
            icon = Icons.Default.HighQuality,
            title = "Quality",
            value = displayQuality,
            onClick = { onNavigate(PlayerOptionsPage.QUALITY) }
        )

        // Audio Track
        OptionRow(
            icon = Icons.Default.Audiotrack,
            title = "Audio Track",
            value = selectedAudioLanguage,
            onClick = { onNavigate(PlayerOptionsPage.AUDIO_TRACK) }
        )

        // Captions / Subtitles
        OptionRow(
            icon = Icons.Default.ClosedCaption,
            title = "Captions",
            value = selectedSubtitleName,
            onClick = { onNavigate(PlayerOptionsPage.CAPTIONS) }
        )

        // Playback Speed
        OptionRow(
            icon = Icons.Default.Speed,
            title = "Playback Speed",
            value = if (playbackSpeed == 1.0f) "Normal" else "${playbackSpeed}x",
            onClick = { onNavigate(PlayerOptionsPage.SPEED) }
        )

        // Sleep Timer
        OptionRow(
            icon = Icons.Default.Bedtime,
            title = "Sleep Timer",
            value = when (sleepTimerMinutes) {
                null -> "Off"
                -1 -> "End of video"
                else -> "$sleepTimerMinutes min"
            },
            onClick = { onNavigate(PlayerOptionsPage.SLEEP_TIMER) }
        )

        // Loop Video
        ListItem(
            headlineContent = { Text("Loop Video") },
            leadingContent = { Icon(Icons.Default.Repeat, contentDescription = null) },
            trailingContent = {
                Switch(
                    checked = isLooping,
                    onCheckedChange = { onToggleLoop() }
                )
            },
            modifier = Modifier.clickable { onToggleLoop() }
        )

        // SponsorBlock
        ListItem(
            headlineContent = { Text("Auto-Skip Sponsors") },
            supportingContent = { Text("Automatically skip sponsor segments", style = MaterialTheme.typography.bodySmall) },
            leadingContent = { Icon(Icons.Default.Shield, contentDescription = null, tint = MaterialTheme.colorScheme.primary) },
            trailingContent = {
                Switch(
                    checked = sponsorBlockEnabled,
                    onCheckedChange = { onToggleSponsorBlock() }
                )
            },
            modifier = Modifier.clickable { onToggleSponsorBlock() }
        )
    }
}

@Composable
private fun OptionRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    value: String,
    onClick: () -> Unit,
) {
    ListItem(
        headlineContent = { Text(title) },
        leadingContent = { Icon(icon, contentDescription = null) },
        trailingContent = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold,
                )
                Spacer(Modifier.width(8.dp))
                Icon(
                    Icons.AutoMirrored.Filled.ArrowForwardIos,
                    contentDescription = null,
                    modifier = Modifier.size(14.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        },
        modifier = Modifier.clickable(onClick = onClick)
    )
}

@Composable
private fun SubMenuSheet(
    title: String,
    onBack: () -> Unit,
    items: List<String>,
    selectedItem: String,
    resolvedQuality: String = "",
    onSelect: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 32.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )
        }
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))

        LazyColumn {
            items(items) { item ->
                val isSelected = item.equals(selectedItem, ignoreCase = true)
                val label = if (item.equals("Auto", ignoreCase = true) && resolvedQuality.isNotBlank()) {
                    "Auto ($resolvedQuality)"
                } else {
                    item
                }
                ListItem(
                    headlineContent = {
                        Text(
                            text = label,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                        )
                    },
                    trailingContent = {
                        if (isSelected) {
                            Icon(Icons.Default.Check, contentDescription = "Selected", tint = MaterialTheme.colorScheme.primary)
                        }
                    },
                    modifier = Modifier.clickable { onSelect(item) }
                )
            }
        }
    }
}

@Composable
private fun CaptionsSubMenuSheet(
    availableSubtitles: List<Subtitle>,
    selectedSubtitle: Subtitle?,
    onBack: () -> Unit,
    onSelect: (Subtitle?) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 32.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
            Text(
                text = "Captions",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )
        }
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))

        LazyColumn {
            item {
                val isOff = selectedSubtitle == null
                ListItem(
                    headlineContent = {
                        Text(
                            "Off",
                            fontWeight = if (isOff) FontWeight.Bold else FontWeight.Normal,
                            color = if (isOff) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                        )
                    },
                    trailingContent = {
                        if (isOff) Icon(Icons.Default.Check, contentDescription = "Selected", tint = MaterialTheme.colorScheme.primary)
                    },
                    modifier = Modifier.clickable { onSelect(null) }
                )
            }
            items(availableSubtitles) { sub ->
                val isSelected = selectedSubtitle?.code == sub.code
                ListItem(
                    headlineContent = {
                        Text(
                            sub.name,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                        )
                    },
                    trailingContent = {
                        if (isSelected) Icon(Icons.Default.Check, contentDescription = "Selected", tint = MaterialTheme.colorScheme.primary)
                    },
                    modifier = Modifier.clickable { onSelect(sub) }
                )
            }
        }
    }
}

@Composable
private fun SpeedSubMenuSheet(
    currentSpeed: Float,
    onBack: () -> Unit,
    onSelect: (Float) -> Unit,
) {
    val speeds = listOf(0.25f, 0.5f, 0.75f, 1.0f, 1.25f, 1.5f, 1.75f, 2.0f)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 32.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
            Text(
                text = "Playback Speed",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )
        }
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))

        LazyColumn {
            items(speeds) { speed ->
                val isSelected = (speed == currentSpeed)
                val label = if (speed == 1.0f) "Normal (1.0x)" else "${speed}x"
                ListItem(
                    headlineContent = {
                        Text(
                            label,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                        )
                    },
                    trailingContent = {
                        if (isSelected) Icon(Icons.Default.Check, contentDescription = "Selected", tint = MaterialTheme.colorScheme.primary)
                    },
                    modifier = Modifier.clickable { onSelect(speed) }
                )
            }
        }
    }
}

@Composable
private fun SleepTimerSubMenuSheet(
    currentMinutes: Int?,
    onBack: () -> Unit,
    onSelect: (Int?) -> Unit,
) {
    val options = listOf(
        null to "Off",
        -1 to "End of video",
        10 to "10 minutes",
        15 to "15 minutes",
        30 to "30 minutes",
        45 to "45 minutes",
        60 to "60 minutes"
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 32.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
            Text(
                text = "Sleep Timer",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )
        }
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))

        LazyColumn {
            items(options) { (minutes, label) ->
                val isSelected = (minutes == currentMinutes)
                ListItem(
                    headlineContent = {
                        Text(
                            label,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                        )
                    },
                    trailingContent = {
                        if (isSelected) Icon(Icons.Default.Check, contentDescription = "Selected", tint = MaterialTheme.colorScheme.primary)
                    },
                    modifier = Modifier.clickable { onSelect(minutes) }
                )
            }
        }
    }
}
