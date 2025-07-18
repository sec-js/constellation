# Hierarchy

<table class="table table-striped">
<colgroup>
<col style="width: 25%" />
<col style="width: 25%" />
<col style="width: 25%" />
<col style="width: 25%" />
</colgroup>
<thead>
<tr class="header">
<th>Constellation Action</th>
<th>Keyboard Shortcut</th>
<th>User Action</th>
<th style="text-align: center;">Menu Icon</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td>Run Hierarchy Arrangement</td>
<td>Ctrl + H</td>
<td>Arrange -&gt; Hierarchy</td>
<td style="text-align: center;"><img src="../ext/docs/CoreArrangementPlugins/resources/arrangeInHierarchy.png" alt="Hierarchy Arrangement Icon" /></td>
</tr>
</tbody>
</table>

The hierarchy arrangement arranges each component into a hierarchical
structure with specified roots at the top and each subsequent level
containing nodes that are the same distance away from the roots (i.e. if
the root nodes are at Level 0, then Level n contains nodes that are n
hops away from the root). Singleton nodes are arranged together in a
grid, similarly for doublets (pairs of nodes only connected to each
other).

The roots may be specified either via a Named Selection (refer
[here](../ext/docs/CoreNamedSelectionView/named-selections-view.md)
for creating a named selection), or by pre-selecting nodes to use as the roots prior to running the arrangement.  
Note: when using pre-selected nodes as the roots, the arrangement will always be run across on the entire graph.  
Otherwise, when you have some pre-selected nodes and choose to use a Named Selection for the roots,  
the arrangement will only be applied to the selected nodes, using the nodes defined in the Named Selection as the roots.  
Only one component is required to have its root(s) specified in order to run the hierarchy arrangement.  
Any component without a specified root will have an arbitrary node chosen to be the root.

Example Hierarchy Arrangement:

<div style="text-align: center">
    <figure style = "display: inline-block">
        <img height=400 src="../ext/docs/CoreArrangementPlugins/resources/BeforeHierarchyArrangement.png" alt="Before Hierarchy Arrangement" />
        <figcaption>Before Hierarchy Arrangement</figcaption>
    </figure>
    <figure style = "display: inline-block">
        <img height=400 src="../ext/docs/CoreArrangementPlugins/resources/HierarchyArrangement.png" alt="Example Hierarchy Arrangement" />
        <figcaption>After Hierarchy Arrangement</figcaption>
    </figure>
</div>