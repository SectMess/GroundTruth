package ui_missionlist.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.astute.core.domain.FilterOrder
import com.astute.mission_domain.MissionAttribute
import com.astute.mission_domain.MissionFilter
import com.astute.ui_missionlist.R
import ui_missionlist.ui.test.*


@ExperimentalAnimationApi
@Composable
fun MissionListFilter(
    missionFilter: MissionFilter,
    onUpdateMissionFilter: (MissionFilter) -> Unit,
    attributeFilter: MissionAttribute = MissionAttribute.Unknown,
    onUpdateAttributeFilter: (MissionAttribute) -> Unit,
    onCloseDialog: () -> Unit,
){
    AlertDialog(
        modifier = Modifier
            .padding(16.dp)
            .testTag(TAG_MISSION_FILTER_DIALOG)
        ,
        onDismissRequest = {
            onCloseDialog()
        },
        title = {
            Text(
                text = "Filter",
                style = MaterialTheme.typography.h2,
            )
        },
        text = {
            LazyColumn {
                item{
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ){

                        // Spacer isn't working for some reason so use Row to create space
                        EmptyRow()

                        // Mission Filter
                        MissionFilterSelector(
                            filterOnMission = {
                                onUpdateMissionFilter(MissionFilter.Mission())
                            },
                            isEnabled = missionFilter is MissionFilter.Mission,
                            order = if(missionFilter is MissionFilter.Mission) missionFilter.order else null,
                            orderDesc = {
                                onUpdateMissionFilter(
                                    MissionFilter.Mission(
                                        order = FilterOrder.Descending
                                    )
                                )
                            },
                            orderAsc = {
                                onUpdateMissionFilter(
                                    MissionFilter.Mission(
                                        order = FilterOrder.Ascending
                                    )
                                )
                            }
                        )

                        ProWinsFilterSelector(
                            filterOnProWins = {
                                onUpdateMissionFilter(
                                    MissionFilter.ProWins()
                                )
                            },
                            isEnabled = missionFilter is MissionFilter.ProWins,
                            order = if(missionFilter is MissionFilter.ProWins) missionFilter.order else null,
                            orderDesc = {
                                onUpdateMissionFilter(
                                    MissionFilter.ProWins(
                                        order = FilterOrder.Descending
                                    )
                                )
                            },
                            orderAsc = {
                                onUpdateMissionFilter(
                                    MissionFilter.ProWins(
                                        order = FilterOrder.Ascending
                                    )
                                )
                            },
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        Divider(
                            color = MaterialTheme.colors.onBackground.copy(alpha = 0.5f),
                            thickness = 1.dp
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        // Primary Attribute Filter
                        PrimaryAttrFilterSelector(
                            removeFilterOnPrimaryAttr = {
                                onUpdateAttributeFilter(MissionAttribute.Unknown)
                            },
                            attribute = attributeFilter,
                            onFilterStr = {
                                onUpdateAttributeFilter(
                                    MissionAttribute.Strength
                                )
                            },
                            onFilterAgi = {
                                onUpdateAttributeFilter(
                                    MissionAttribute.Agility
                                )
                            },
                            onFilterInt = {
                                onUpdateAttributeFilter(
                                    MissionAttribute.Intelligence
                                )
                            },
                        )
                    }
                }
            }
        },
        buttons = {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Row( // make the icon larger so it's easier to click
                    modifier = Modifier
                        .align(Alignment.End)
                        .testTag(TAG_MISSION_FILTER_DIALOG_DONE)
                        .clickable {
                            onCloseDialog()
                        }
                    ,
                ){
                    Icon(
                        modifier = Modifier
                            .padding(10.dp)
                        ,
                        imageVector = Icons.Default.Check,
                        contentDescription = "Done",
                        tint = Color(0xFF009a34)
                    )
                }

            }
        }
    )
}

/**
 * @param filterOnMission: Set the MissionFilter to 'Mission'
 * @param isEnabled: Is the Mission filter the selected 'MissionFilter'
 * @param order: Ascending or Descending?
 * @param orderDesc: Set the order to descending.
 * @param orderAsc: Set the order to ascending.
 */
@ExperimentalAnimationApi
@Composable
fun MissionFilterSelector(
    filterOnMission: () -> Unit,
    isEnabled: Boolean,
    order: FilterOrder? = null,
    orderDesc: () -> Unit,
    orderAsc: () -> Unit,
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
                .testTag(TAG_MISSION_FILTER_MISSION_CHECKBOX)
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null, // disable the highlight
                    enabled = true,
                    onClick = {
                        filterOnMission()
                    },
                )
            ,
        ){
            Checkbox(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .align(Alignment.CenterVertically)
                ,
                checked = isEnabled,
                onCheckedChange = {
                    filterOnMission()
                },
                colors = CheckboxDefaults.colors(MaterialTheme.colors.primary)
            )
            Text(
                text = MissionFilter.Mission().uiValue,
                style = MaterialTheme.typography.h3,
            )
        }

        OrderSelector(
            descString = "z -> a",
            ascString = "a -> z",
            isEnabled = isEnabled,
            isDescSelected = isEnabled && order is FilterOrder.Descending,
            isAscSelected = isEnabled && order is FilterOrder.Ascending,
            onUpdateMissionFilterDesc = {
                orderDesc()
            },
            onUpdateMissionFilterAsc = {
                orderAsc()
            },
        )
    }
}

/**
 * @param filterOnProWins: Set the MissionFilter to 'ProWins'
 * @param isEnabled: Is the ProWins filter the selected 'MissionFilter'
 * @param order: Ascending or Descending?
 * @param orderDesc: Set the order to descending.
 * @param orderAsc: Set the order to ascending.
 */
@ExperimentalAnimationApi
@Composable
fun ProWinsFilterSelector(
    filterOnProWins: () -> Unit,
    isEnabled: Boolean,
    order: FilterOrder? = null,
    orderDesc: () -> Unit,
    orderAsc: () -> Unit,
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ){
        Row(
            modifier = Modifier
                .padding(bottom = 12.dp)
                .fillMaxWidth()
                .testTag(TAG_MISSION_FILTER_PROWINS_CHECKBOX)
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null, // disable the highlight
                    enabled = true,
                    onClick = {
                        filterOnProWins()
                    },
                )
            ,
        ){
            Checkbox(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .align(Alignment.CenterVertically)
                ,
                checked = isEnabled,
                onCheckedChange = {
                    filterOnProWins()
                },
                colors = CheckboxDefaults.colors(MaterialTheme.colors.primary)
            )
            Text(
                text = MissionFilter.ProWins().uiValue,
                style = MaterialTheme.typography.h3,
            )
        }

        OrderSelector(
            descString = "100% - 0%",
            ascString = "0% - 100%",
            isEnabled = isEnabled,
            isDescSelected = isEnabled && order is FilterOrder.Descending,
            isAscSelected = isEnabled && order is FilterOrder.Ascending,
            onUpdateMissionFilterDesc = {
                orderDesc()
            },
            onUpdateMissionFilterAsc = {
                orderAsc()
            },
        )
    }
}

/**
 * @param filterOnPrimaryAttr: Set the MissionFilter to 'PrimaryAttribute'
 * @param isEnabled: Is the PrimaryAttribute filter the selected 'MissionFilter'
 * @param attribute: Is the current attribute Strength, Agility or Intelligence?
 * @param orderStr: Set the order to Strength.
 * @param orderAgi: Set the order to Agility.
 * @param orderInt: Set the order to Intelligence.
 */
@ExperimentalAnimationApi
@Composable
fun PrimaryAttrFilterSelector(
    removeFilterOnPrimaryAttr: () -> Unit,
    attribute: MissionAttribute,
    onFilterStr: () -> Unit,
    onFilterAgi: () -> Unit,
    onFilterInt: () -> Unit,
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ){
        Row(
            modifier = Modifier
                .padding(bottom = 12.dp)
                .fillMaxWidth()
            ,
        ){
            Text(
                text = stringResource(R.string.primary_attribute),
                style = MaterialTheme.typography.h3,
            )
        }

        PrimaryAttrSelector(
            isStr = attribute is MissionAttribute.Strength,
            isAgi = attribute is MissionAttribute.Agility,
            isInt = attribute is MissionAttribute.Intelligence,
            onUpdateMissionFilterStr = {
                onFilterStr()
            },
            onUpdateMissionFilterAgi = {
                onFilterAgi()
            },
            onUpdateMissionFilterInt = {
                onFilterInt()
            },
            onRemoveAttributeFilter = {
                removeFilterOnPrimaryAttr()
            }
        )
    }
}

/**
 * @param isStr: Is the selected attribute strength?
 * @param isAgi: Is the selected attribute Agility?
 * @param isInt: Is the selected attribute Intelligence?
 * @param onUpdateMissionFilterStr: Update the filter to Strength
 * @param onUpdateMissionFilterAgi: Update the filter to Agility
 * @param onUpdateMissionFilterInt: Update the filter to Intelligence
 */
@ExperimentalAnimationApi
@Composable
fun PrimaryAttrSelector(
    isStr: Boolean = false,
    isAgi: Boolean = false,
    isInt: Boolean = false,
    onUpdateMissionFilterStr: () -> Unit,
    onUpdateMissionFilterAgi: () -> Unit,
    onUpdateMissionFilterInt: () -> Unit,
    onRemoveAttributeFilter: () -> Unit,
){
    // Strength
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, bottom = 8.dp)
            .testTag(TAG_MISSION_FILTER_STENGTH_CHECKBOX)
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null, // disable the highlight
                onClick = {
                    onUpdateMissionFilterStr()
                },
            )
        ,
    ){
        Checkbox(
            modifier = Modifier
                .padding(end = 8.dp)
                .align(Alignment.CenterVertically)
            ,
            checked = isStr,
            onCheckedChange = {
                onUpdateMissionFilterStr()
            },
            colors = CheckboxDefaults.colors(MaterialTheme.colors.primary)
        )
        Text(
            text = MissionAttribute.Strength.uiValue,
            style = MaterialTheme.typography.body1,
        )
    }

    // Agility
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, bottom = 8.dp)
            .testTag(TAG_MISSION_FILTER_AGILITY_CHECKBOX)
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null, // disable the highlight
                onClick = {
                    onUpdateMissionFilterAgi()
                },
            )
        ,
    ){
        Checkbox(
            modifier = Modifier
                .padding(end = 8.dp)
                .align(Alignment.CenterVertically)
            ,
            checked = isAgi,
            onCheckedChange = {
                onUpdateMissionFilterAgi()
            },
            colors = CheckboxDefaults.colors(MaterialTheme.colors.primary)
        )
        Text(
            text = MissionAttribute.Agility.uiValue,
            style = MaterialTheme.typography.body1,
        )
    }

    // Intelligence
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, bottom = 8.dp)
            .testTag(TAG_MISSION_FILTER_INT_CHECKBOX)
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null, // disable the highlight
                onClick = {
                    onUpdateMissionFilterInt()
                },
            )
        ,
    ){
        Checkbox(
            modifier = Modifier
                .padding(end = 8.dp)
                .align(Alignment.CenterVertically)
            ,
            checked = isInt,
            onCheckedChange = {
                onUpdateMissionFilterInt()
            },
            colors = CheckboxDefaults.colors(MaterialTheme.colors.primary)
        )
        Text(
            text = MissionAttribute.Intelligence.uiValue,
            style = MaterialTheme.typography.body1,
        )
    }

    // No Filter on Attribute
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, bottom = 8.dp)
            .testTag(TAG_MISSION_FILTER_UNKNOWN_CHECKBOX)
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null, // disable the highlight
                onClick = {
                    onRemoveAttributeFilter()
                },
            )
        ,
    ){
        Checkbox(
            modifier = Modifier
                .padding(end = 8.dp)
                .align(Alignment.CenterVertically)
            ,
            checked = !isStr && !isAgi && !isInt,
            onCheckedChange = {
                onRemoveAttributeFilter()
            },
            colors = CheckboxDefaults.colors(MaterialTheme.colors.primary)
        )
        Text(
            text = stringResource(R.string.none),
            style = MaterialTheme.typography.body1,
        )
    }
}