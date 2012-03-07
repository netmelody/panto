Panto
=====
Panto is parallel ant operations... Oh no it isn't!

Oh yes it is, you can [download] here.

#### Striped Resource Collections
Panto's striped resources allow for parallelisation of many ant tasks.

``` XML
  <typedef name="stripedresources"
           classname="org.netmelody.panto.type.StripedResourceCollection"
           classpath="path/to/panto-0.0.1.jar" />

  <fileset id="test.files" dir="my/test/dir" includes="**/*Test.class" />
  <parallel>
    <junit>
      <batchtest>
        <stripedresources stripeCount="2" stripeNum="1">
          <fileset refid="test.files"/>
        </stripedresources>
      </batchtest>
    </junit>
    <junit>
      <batchtest>
        <stripedresources stripeCount="2" stripeNum="2">
          <fileset refid="test.files"/>
        </stripedresources>
      </batchtest>
    </junit>
  </parallel>
```
#### Striped JUnit Testing
Panto's striped JUnit task makes parallelising junit tests easy.

``` XML
  <taskdef name="stripedjunit"
           classname="org.netmelody.panto.task.StripedJUnitTask"
           classpath="path/to/panto-0.0.1.jar" />

  <stripedjunit>
      <junit /> <!-- substitute your normal junit ant task here -->
  </stripedjunit>
```

[download]: https://github.com/netmelody/panto/downloads
