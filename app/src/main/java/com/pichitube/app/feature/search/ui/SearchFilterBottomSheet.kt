package com.pichitube.app.feature.search.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pichitube.app.feature.search.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchFilterBottomSheet(
    currentFilter: SearchFilter,
    onApply: (SearchFilter) -> Unit,
    onReset: () -> Unit,
    onDismiss: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var tempFilter by remember(currentFilter) { mutableStateOf(currentFilter) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Color(0xFF1E1E1E),
        contentColor = Color.White,
        dragHandle = { BottomSheetDefaults.DragHandle(color = Color(0xFF717171)) },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 20.dp, vertical = 8.dp)
        ) {
            // Header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Search filters",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                )
                IconButton(onClick = onDismiss) {
                    Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
                }
            }

            Spacer(Modifier.height(8.dp))

            Column(
                modifier = Modifier
                    .weight(1f, fill = false)
                    .verticalScroll(rememberScrollState())
            ) {
                // Section 1: Sort By
                FilterSectionHeader("SORT BY")
                FilterChipGroup(
                    options = SearchSort.entries.map { it to it.label },
                    selected = tempFilter.sortBy,
                    onSelected = { tempFilter = tempFilter.copy(sortBy = it) }
                )

                Spacer(Modifier.height(18.dp))

                // Section 2: Type
                FilterSectionHeader("TYPE")
                FilterChipGroup(
                    options = SearchType.entries.map { it to it.label },
                    selected = tempFilter.type,
                    onSelected = { tempFilter = tempFilter.copy(type = it) }
                )

                Spacer(Modifier.height(18.dp))

                // Section 3: Upload Date
                FilterSectionHeader("UPLOAD DATE")
                FilterChipGroup(
                    options = SearchUploadDate.entries.map { it to it.label },
                    selected = tempFilter.uploadDate,
                    onSelected = { tempFilter = tempFilter.copy(uploadDate = it) }
                )

                Spacer(Modifier.height(18.dp))

                // Section 4: Duration
                FilterSectionHeader("DURATION")
                FilterChipGroup(
                    options = SearchDuration.entries.map { it to it.label },
                    selected = tempFilter.duration,
                    onSelected = { tempFilter = tempFilter.copy(duration = it) }
                )

                Spacer(Modifier.height(20.dp))
            }

            // Bottom Actions: Reset and Apply
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onReset,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.White
                    ),
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        brush = androidx.compose.ui.graphics.SolidColor(Color(0xFF555555))
                    )
                ) {
                    Text("Reset", fontWeight = FontWeight.SemiBold)
                }

                Button(
                    onClick = { onApply(tempFilter) },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF0033),
                        contentColor = Color.White
                    )
                ) {
                    Text("Apply", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun FilterSectionHeader(title: String) {
    Text(
        text = title,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFFAAAAAA),
        letterSpacing = 1.sp,
        modifier = Modifier.padding(vertical = 6.dp)
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun <T> FilterChipGroup(
    options: List<Pair<T, String>>,
    selected: T,
    onSelected: (T) -> Unit,
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        options.forEach { (value, label) ->
            val isSelected = (value == selected)
            Surface(
                shape = RoundedCornerShape(18.dp),
                color = if (isSelected) Color.White else Color(0xFF2C2C2C),
                contentColor = if (isSelected) Color.Black else Color.White,
                modifier = Modifier
                    .clip(RoundedCornerShape(18.dp))
                    .clickable { onSelected(value) }
            ) {
                Text(
                    text = label,
                    fontSize = 13.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                )
            }
        }
    }
}
