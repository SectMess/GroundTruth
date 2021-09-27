package ui_missionlist.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import ui_missionlist.ui.test.TAG_MISSION_FILTER_ASC
import ui_missionlist.ui.test.TAG_MISSION_FILTER_DESC

/**
 * @param descString: String displayed in the "descending" checkbox
 * @param ascString: String displayed in the "ascending" checkbox
 * @param isEnabled: Is this MissionFilter currently the selected MissionFilter?
 * @param isDescSelected: Is the "descending" checkbox selected?
 * @param isAscSelected: Is the "ascending" checkbox selected?
 * @param onUpdateMissionFilterDesc: Set the filter to Descending.
 * @param onUpdateMissionFilterAsc: Set the filter to Ascending.
 */
@ExperimentalAnimationApi
@Composable
fun OrderSelector(
    descString: String,
    ascString: String,
    isEnabled: Boolean,
    isDescSelected: Boolean,
    isAscSelected: Boolean,
    onUpdateMissionFilterDesc: () -> Unit,
    onUpdateMissionFilterAsc: () -> Unit,
){
    // Descending Order
    AnimatedVisibility(visible = isEnabled) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, bottom = 8.dp)
                .testTag(TAG_MISSION_FILTER_DESC)
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null, // disable the highlight
                    enabled = isEnabled,
                    onClick = {
                        onUpdateMissionFilterDesc()
                    },
                )
            ,
        ){
            Checkbox(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .align(Alignment.CenterVertically)
                ,
                enabled= isEnabled,
                checked = isEnabled && isDescSelected,
                onCheckedChange = {
                    onUpdateMissionFilterDesc()
                },
                colors = CheckboxDefaults.colors(MaterialTheme.colors.primary)
            )
            Text(
                text = descString,
                style = MaterialTheme.typography.body1,
            )
        }
    }

    // Ascending Order
    AnimatedVisibility(visible = isEnabled) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, bottom = 8.dp)
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null, // disable the highlight
                    enabled = isEnabled,
                    onClick = {
                        onUpdateMissionFilterAsc()
                    },
                )
            ,
        ){
            Checkbox(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .testTag(TAG_MISSION_FILTER_ASC)
                    .align(Alignment.CenterVertically)
                ,
                enabled= isEnabled,
                checked = isEnabled && isAscSelected,
                onCheckedChange = {
                    onUpdateMissionFilterAsc()
                },
                colors = CheckboxDefaults.colors(MaterialTheme.colors.primary)
            )
            Text(
                text = ascString,
                style = MaterialTheme.typography.body1,
            )
        }
    }
}