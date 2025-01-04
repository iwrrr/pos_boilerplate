package com.example.pos_boilerplate.features.receipt

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Print
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pos_boilerplate.core.common.state.Async
import com.example.pos_boilerplate.core.common.utils.toRupiah
import com.example.pos_boilerplate.core.domain.model.ReceiptItem
import com.example.pos_boilerplate.features.cart.CartIntent
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ReceiptScreen(
    receiptId: String,
    navigateBack: () -> Unit,
    viewModel: ReceiptViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sendIntent(ReceiptIntent.GetReceipt(receiptId))
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Kuitansi")
                },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBackIosNew,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        bottomBar = {
            when (state.asyncInvoice) {
                is Async.Success -> {
                    BottomAppBar(
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) {
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                            }
                        ) {
                            Icon(imageVector = Icons.Outlined.Print, contentDescription = null)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "Print Receipt")
                        }
                    }
                }

                else -> {}
            }
        }
    ) {
        when (val async = state.asyncInvoice) {
            is Async.Default -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "DEFAULT")
                }
            }

            is Async.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "LOADING")
                }
            }

            is Async.Success -> {
                Card(
                    modifier = Modifier
                        .padding(it)
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    LazyColumn(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        item {
                            Text(text = "Title")
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(text = "Description")
                        }

                        item {
                            Column(
                                modifier = Modifier.padding(vertical = 12.dp)
                            ) {
                                Row {
                                    Text(
                                        text = "Transaction ID",
                                    )
                                    Text(
                                        modifier = Modifier.weight(2f),
                                        text = async.data.id,
                                        textAlign = TextAlign.End
                                    )
                                }
                                Spacer(modifier = Modifier.height(2.dp))
                                Row {
                                    Text(
                                        text = "Tanggal"
                                    )
                                    Text(
                                        modifier = Modifier.weight(2f),
                                        text = formatToDate(
                                            timestamp = async.data.createdAt,
                                            pattern = "dd MMMM yyyy, HH:mm"
                                        ),
                                        textAlign = TextAlign.End
                                    )
                                }
                            }
                        }

                        items(items = async.data.items) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(text = it.productName)
                                    Text(text = "${it.quantity}x")
                                }
                                Text(
                                    modifier = Modifier.weight(2f),
                                    text = it.subtotal.toRupiah(),
                                    textAlign = TextAlign.End
                                )
                            }
                        }

                        item {
                            Column(
                                modifier = Modifier.padding(vertical = 12.dp)
                            ) {
                                HorizontalDivider()
                                Spacer(modifier = Modifier.height(12.dp))
                                Row {
                                    Text(
                                        text = "Subtotal",
                                    )
                                    Text(
                                        modifier = Modifier.weight(2f),
                                        text = state.subtotal.toRupiah(),
                                        textAlign = TextAlign.End
                                    )
                                }
                                Spacer(modifier = Modifier.height(2.dp))
                                Row {
                                    Text(
                                        text = "PPN (12%)"
                                    )
                                    Text(
                                        modifier = Modifier.weight(2f),
                                        text = state.tax.toRupiah(),
                                        textAlign = TextAlign.End
                                    )
                                }
                                Spacer(modifier = Modifier.height(2.dp))
                                Row {
                                    Text(
                                        text = "Total"
                                    )
                                    Text(
                                        modifier = Modifier.weight(2f),
                                        text = state.total.toRupiah(),
                                        textAlign = TextAlign.End
                                    )
                                }
                            }
                        }
                    }
                }
            }

            is Async.Failure -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "${async.throwable.message}")
                }
            }
        }
    }
}

fun formatToDate(timestamp: Long, pattern: String = "dd MMM yyyy"): String {
    val sdf = SimpleDateFormat(pattern, Locale("id"))
    return sdf.format(Date(timestamp))
}