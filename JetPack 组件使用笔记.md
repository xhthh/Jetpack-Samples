### JetPack 组件使用笔记
https://juejin.cn/post/6955491901265051661#heading-16

#### 一、DataBinding

https://juejin.cn/post/6980585971188236295

https://www.jianshu.com/p/e8b6ba90de53

##### 1、gradle 配置

```groovy
apply plugin: 'kotlin-kapt'//当需要用到注解时添加

android {
   //code...
    dataBinding {
        enabled true //启用dataBinding
    }
	//dataBinding.enabled = true  这样也可以
	//code...
}

```

##### 2、xml 布局配置

```xml
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">

    <data>
        <variable
            name="user"
            type="com.xht.jetpack.databinding.User" />
    </data>
    <原始页面根布局/>
</layout>
```

选中要改造的布局，Alt+Enter 选择 Convert to data binding layout

###### 2.1 `<layout>`标签

使用 DataBinding 布局必须被该标签包裹，里面只有两个子标签，`<data>` 和 原始页面根布局。

被 DataBinding 识别的布局文件都会自动生成一个绑定类，该类名称由布局文件名转化而来：

> 下划线语法变程驼峰语法+Binding：
>
> activity_main.xml 得到的绑定类是 ActivityMainBinding

```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    //setContentView方法中需要传入泛型参数，即由布局文件生成的绑定类
    val binding = DataBindingUtil.setContentView<ActivityDatabindingBinding>(this,
        R.layout.activity_databinding)
    binding.user = User("xht", 18)
}
```



