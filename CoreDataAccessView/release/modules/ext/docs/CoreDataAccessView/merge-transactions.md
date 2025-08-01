# Merge transactions

## Merge transactions in your graph together based on a defined criteria

<br />
<img src="../ext/docs/CoreDataAccessView/resources/MergeTransactions.png" alt="Merge Transactions" />
<br />

This plugin is used to merge groups of transactions between the same two
nodes. Transactions must have the same type (and, if it exists,
activity) in order to be a chance of merging.

## Parameters

-   *Merge By* - The rule to determine when transactions should be
    merged together
-   *Threshold* - The limit to apply to the selected rule. Transactions are merged if "Merge By" rule's value is less than or equal to threshold
-   *Merging Rule* - The rule deciding how attributes are merged
-   *Lead Transaction* - The rule deciding how to choose the lead
    transaction. Options include:
    -   Latest Time - Once merged, the latest transaction will remain
    -   Earliest Time - Once merged, the earliest transaction will
        remain
-   *Selected Only* - Only apply to selected transactions
