Panto
=====
Panto is parallel ant operations... Oh no it isn't!

#### Striped Resource Collections
``` XML
  <typedef name="stripedresources"
           classname="org.netmelody.panto.StripedResourceCollection"
           classpath="path/to/panto-0.0.1.jar" />

  <someResourcefulTask>
    <stripedresources stripeCount="3"
                      stripeNum="${stripe}"> <!-- stripe can be 1-3 here -->
      <fileset /> <!-- substitute normal resources here -->
    </stripedresources>
  </someResourcefulTask>
```
#### Striped JUnit Testing
``` XML
  <taskdef name="stripedjunit"
           classname="org.netmelody.panto.StripedJUnitTask"
           classpath="path/to/panto-0.0.1.jar" />

  <stripedjunit>
      <junit /> <!-- substitute your normal junit ant task here -->
  </stripedjunit>
```
