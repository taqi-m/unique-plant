package com.app.uniqueplant.presentation.admin.transaction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.uniqueplant.data.model.Expense
import com.app.uniqueplant.data.model.Income
import com.app.uniqueplant.domain.usecase.CurrencyFormaterUseCase
import com.app.uniqueplant.ui.components.cards.ExpenseCard
import com.app.uniqueplant.ui.components.cards.IncomeCard
import com.app.uniqueplant.ui.components.dialogs.DeleteDialog
import com.app.uniqueplant.ui.theme.UniquePlantTheme

@Composable
fun TransactionsScreen(
    state: TransactionState,
    onEvent: (TransactionEvent) -> Unit,
) {
    val transactions by state.transactions.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TransactionHeading(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp).padding(top = 8.dp),
                currentBalance = CurrencyFormaterUseCase.formatCurrency(state.incoming - state.outgoing),
                incoming = CurrencyFormaterUseCase.formatCurrency(state.incoming),
                outgoing = CurrencyFormaterUseCase.formatCurrency(state.outgoing)
            )
            TransactionList(
                transactions = transactions,
                onEvent = onEvent
            )
        }
        DeleteDialog(
            isVisible = state.isDeleteMode,
            onDismissRequest = { onEvent(TransactionEvent.OnDeleteModeToggle) },
            onDeleteConfirmed = { onEvent(TransactionEvent.OnTransactionDeleted) }
        )
    }
}


@Composable
fun TransactionList(
    transactions: List<Any>,
    onEvent: (TransactionEvent) -> Unit
) {
    for (transaction in transactions) {
        when (transaction) {
            is Expense -> {
                ExpenseTransaction(
                    expense = transaction,
                    onEvent = onEvent
                )
            }

            is Income -> {
                IncomeTransaction(
                    income = transaction,
                    onEvent = onEvent
                )
            }
        }
    }
}

@Composable
fun ExpenseTransaction(
    expense: Expense,
    onEvent: (TransactionEvent) -> Unit,
) {
    ExpenseCard(
        expense = expense.amount,
        date = expense.date,
        onEditClicked = {
            onEvent(TransactionEvent.OnTransactionSelected(expense))
            onEvent(TransactionEvent.OnEditModeToggle)
        },
        onDeleteClicked = {
            onEvent(TransactionEvent.OnTransactionSelected(expense))
            onEvent(TransactionEvent.OnDeleteModeToggle)
        }
    )
}

@Composable
fun IncomeTransaction(
    income: Income,
    onEvent: (TransactionEvent) -> Unit,
) {
    IncomeCard(
        income = income.amount,
        date = income.date,
        onEditClicked = {
            onEvent(TransactionEvent.OnTransactionSelected(income))
            onEvent(TransactionEvent.OnEditModeToggle)
        },
        onDeleteClicked = {
            onEvent(TransactionEvent.OnTransactionSelected(income))
            onEvent(TransactionEvent.OnDeleteModeToggle)
        }
    )
}


@Composable
fun TransactionHeading(
    modifier: Modifier = Modifier,
    currentBalance: String,
    incoming: String,
    outgoing: String
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.large,
            colors = CardDefaults.cardColors().copy(
                containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = "Total Balance",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .padding(top = 8.dp)
                )
                Text(
                    text = currentBalance,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                )
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.outlineVariant
                )
                Row {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Incoming",
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = incoming,
                            style = MaterialTheme.typography.titleLarge,
                            textAlign = TextAlign.Center
                        )
                    }
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Outgoing",
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = outgoing,
                            style = MaterialTheme.typography.titleLarge,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
)
@Composable
fun TransactionHeadingPreview() {
    UniquePlantTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {
            TransactionHeading(
                modifier = Modifier.fillMaxWidth(),
                currentBalance = CurrencyFormaterUseCase.formatCurrency(3000.00),
                incoming = CurrencyFormaterUseCase.formatCurrency(5000.00), // Replace with actual incoming amount
                outgoing = CurrencyFormaterUseCase.formatCurrency(2000.00) // Replace with actual outgoing amount
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun TransactionsScreenPreview() {
    UniquePlantTheme {
        TransactionsScreen(
            state = TransactionState(),
            onEvent = {}
        )
    }
}
