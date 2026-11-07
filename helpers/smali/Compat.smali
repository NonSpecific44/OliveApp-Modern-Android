.class public final Lolive/app/Compat;
.super Ljava/lang/Object;
.source "Compat.java"


# static fields
.field static final EMPHASIZED:Landroid/view/animation/Interpolator;

.field private static multicastLock:Landroid/net/wifi/WifiManager$MulticastLock;


# direct methods
.method static constructor <clinit>()V
    .locals 5

    .prologue
    .line 39
    new-instance v0, Landroid/view/animation/PathInterpolator;

    const v1, 0x3d4ccccd    # 0.05f

    const v2, 0x3f333333    # 0.7f

    const v3, 0x3dcccccd    # 0.1f

    const/high16 v4, 0x3f800000    # 1.0f

    invoke-direct {v0, v1, v2, v3, v4}, Landroid/view/animation/PathInterpolator;-><init>(FFFF)V

    sput-object v0, Lolive/app/Compat;->EMPHASIZED:Landroid/view/animation/Interpolator;

    return-void
.end method

.method private constructor <init>()V
    .locals 0

    .prologue
    .line 41
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 42
    return-void
.end method

.method static synthetic access$000(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)I
    .locals 1

    .prologue
    .line 35
    invoke-static {p0, p1, p2}, Lolive/app/Compat;->id(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)I

    move-result v0

    return v0
.end method

.method static synthetic access$100(Landroid/app/TabActivity;Landroid/view/View;Ljava/lang/String;)Landroid/view/MenuItem;
    .locals 1

    .prologue
    .line 35
    invoke-static {p0, p1, p2}, Lolive/app/Compat;->findTabMenuItem(Landroid/app/TabActivity;Landroid/view/View;Ljava/lang/String;)Landroid/view/MenuItem;

    move-result-object v0

    return-object v0
.end method

.method static synthetic access$200(Landroid/app/TabActivity;I)Landroid/view/View;
    .locals 1

    .prologue
    .line 35
    invoke-static {p0, p1}, Lolive/app/Compat;->selectRailItem(Landroid/app/TabActivity;I)Landroid/view/View;

    move-result-object v0

    return-object v0
.end method

.method public static animateLists(Landroid/app/Activity;)V
    .locals 1

    .prologue
    .line 365
    invoke-virtual {p0}, Landroid/app/Activity;->getWindow()Landroid/view/Window;

    move-result-object v0

    if-nez v0, :cond_1

    const/4 v0, 0x0

    .line 366
    :goto_0
    if-eqz v0, :cond_0

    .line 367
    invoke-static {v0}, Lolive/app/Compat;->watchLists(Landroid/view/View;)V

    .line 369
    :cond_0
    return-void

    .line 365
    :cond_1
    invoke-virtual {p0}, Landroid/app/Activity;->getWindow()Landroid/view/Window;

    move-result-object v0

    invoke-virtual {v0}, Landroid/view/Window;->peekDecorView()Landroid/view/View;

    move-result-object v0

    goto :goto_0
.end method

.method public static buildRail(Landroid/app/TabActivity;)V
    .locals 13

    .prologue
    const/high16 v12, 0x40800000    # 4.0f

    const/4 v5, 0x0

    .line 234
    const-string v0, "id"

    const-string v1, "navRail"

    invoke-static {p0, v0, v1}, Lolive/app/Compat;->id(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)I

    move-result v0

    invoke-virtual {p0, v0}, Landroid/app/TabActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    .line 235
    instance-of v1, v0, Landroid/view/ViewGroup;

    if-nez v1, :cond_0

    .line 267
    :goto_0
    return-void

    .line 238
    :cond_0
    check-cast v0, Landroid/view/ViewGroup;

    .line 239
    invoke-virtual {v0}, Landroid/view/ViewGroup;->removeAllViews()V

    .line 240
    invoke-virtual {p0}, Landroid/app/TabActivity;->getTabHost()Landroid/widget/TabHost;

    move-result-object v6

    .line 241
    invoke-virtual {v6}, Landroid/widget/TabHost;->getTabWidget()Landroid/widget/TabWidget;

    move-result-object v7

    .line 242
    invoke-virtual {p0}, Landroid/app/TabActivity;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    invoke-virtual {v1}, Landroid/content/res/Resources;->getDisplayMetrics()Landroid/util/DisplayMetrics;

    move-result-object v1

    iget v8, v1, Landroid/util/DisplayMetrics;->density:F

    move v4, v5

    .line 243
    :goto_1
    invoke-virtual {v7}, Landroid/widget/TabWidget;->getTabCount()I

    move-result v1

    if-ge v4, v1, :cond_2

    .line 244
    invoke-virtual {v7, v4}, Landroid/widget/TabWidget;->getChildTabViewAt(I)Landroid/view/View;

    move-result-object v2

    .line 245
    const-string v1, "id"

    const-string v3, "tabLabel"

    invoke-static {p0, v1, v3}, Lolive/app/Compat;->id(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)I

    move-result v1

    invoke-virtual {v2, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v1

    check-cast v1, Landroid/widget/TextView;

    .line 246
    const-string v3, "id"

    const-string v9, "tabIcon"

    invoke-static {p0, v3, v9}, Lolive/app/Compat;->id(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)I

    move-result v3

    invoke-virtual {v2, v3}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v2

    check-cast v2, Landroid/widget/ImageView;

    .line 247
    invoke-virtual {p0}, Landroid/app/TabActivity;->getLayoutInflater()Landroid/view/LayoutInflater;

    move-result-object v3

    const-string v9, "layout"

    const-string v10, "olive_tab"

    invoke-static {p0, v9, v10}, Lolive/app/Compat;->id(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)I

    move-result v9

    invoke-virtual {v3, v9, v0, v5}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;Z)Landroid/view/View;

    move-result-object v9

    .line 248
    new-instance v3, Landroid/widget/LinearLayout$LayoutParams;

    const/4 v10, -0x1

    const/4 v11, -0x2

    invoke-direct {v3, v10, v11}, Landroid/widget/LinearLayout$LayoutParams;-><init>(II)V

    .line 250
    const/high16 v10, 0x41400000    # 12.0f

    mul-float/2addr v10, v8

    invoke-static {v10}, Ljava/lang/Math;->round(F)I

    move-result v10

    iput v10, v3, Landroid/widget/LinearLayout$LayoutParams;->topMargin:I

    .line 251
    invoke-virtual {v9, v3}, Landroid/view/View;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    .line 252
    mul-float v3, v12, v8

    invoke-static {v3}, Ljava/lang/Math;->round(F)I

    move-result v3

    mul-float v10, v12, v8

    invoke-static {v10}, Ljava/lang/Math;->round(F)I

    move-result v10

    invoke-virtual {v9, v5, v3, v5, v10}, Landroid/view/View;->setPadding(IIII)V

    .line 253
    const-string v3, "id"

    const-string v10, "tabIcon"

    invoke-static {p0, v3, v10}, Lolive/app/Compat;->id(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)I

    move-result v3

    invoke-virtual {v9, v3}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v3

    check-cast v3, Landroid/widget/ImageView;

    .line 254
    invoke-virtual {v2}, Landroid/widget/ImageView;->getDrawable()Landroid/graphics/drawable/Drawable;

    move-result-object v10

    if-nez v10, :cond_1

    const/4 v2, 0x0

    .line 253
    :goto_2
    invoke-virtual {v3, v2}, Landroid/widget/ImageView;->setImageDrawable(Landroid/graphics/drawable/Drawable;)V

    .line 255
    const-string v2, "id"

    const-string v3, "tabLabel"

    invoke-static {p0, v2, v3}, Lolive/app/Compat;->id(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)I

    move-result v2

    invoke-virtual {v9, v2}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v2

    check-cast v2, Landroid/widget/TextView;

    invoke-virtual {v1}, Landroid/widget/TextView;->getText()Ljava/lang/CharSequence;

    move-result-object v3

    invoke-virtual {v2, v3}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 256
    invoke-virtual {v1}, Landroid/widget/TextView;->getText()Ljava/lang/CharSequence;

    move-result-object v1

    invoke-virtual {v9, v1}, Landroid/view/View;->setContentDescription(Ljava/lang/CharSequence;)V

    .line 258
    new-instance v1, Lolive/app/Compat$3;

    invoke-direct {v1, v6, v4}, Lolive/app/Compat$3;-><init>(Landroid/widget/TabHost;I)V

    invoke-virtual {v9, v1}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 263
    invoke-virtual {v0, v9}, Landroid/view/ViewGroup;->addView(Landroid/view/View;)V

    .line 243
    add-int/lit8 v1, v4, 0x1

    move v4, v1

    goto/16 :goto_1

    .line 254
    :cond_1
    invoke-virtual {v2}, Landroid/widget/ImageView;->getDrawable()Landroid/graphics/drawable/Drawable;

    move-result-object v2

    invoke-virtual {v2}, Landroid/graphics/drawable/Drawable;->getConstantState()Landroid/graphics/drawable/Drawable$ConstantState;

    move-result-object v2

    invoke-virtual {v2}, Landroid/graphics/drawable/Drawable$ConstantState;->newDrawable()Landroid/graphics/drawable/Drawable;

    move-result-object v2

    goto :goto_2

    .line 265
    :cond_2
    const/16 v0, 0x8

    invoke-virtual {v7, v0}, Landroid/widget/TabWidget;->setVisibility(I)V

    .line 266
    invoke-virtual {v6}, Landroid/widget/TabHost;->getCurrentTab()I

    move-result v0

    invoke-static {p0, v0}, Lolive/app/Compat;->selectRailItem(Landroid/app/TabActivity;I)Landroid/view/View;

    goto/16 :goto_0
.end method

.method public static dedupeSubtitle(Landroid/widget/TextView;)V
    .locals 5

    .prologue
    const/4 v2, 0x0

    .line 157
    invoke-virtual {p0}, Landroid/widget/TextView;->getParent()Landroid/view/ViewParent;

    move-result-object v0

    check-cast v0, Landroid/view/View;

    .line 158
    invoke-virtual {p0}, Landroid/widget/TextView;->getContext()Landroid/content/Context;

    move-result-object v1

    const-string v3, "id"

    const-string v4, "artist"

    invoke-static {v1, v3, v4}, Lolive/app/Compat;->id(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v1

    check-cast v1, Landroid/widget/TextView;

    .line 159
    if-ne v1, p0, :cond_2

    .line 160
    invoke-virtual {p0}, Landroid/widget/TextView;->getContext()Landroid/content/Context;

    move-result-object v1

    const-string v3, "id"

    const-string v4, "album"

    invoke-static {v1, v3, v4}, Lolive/app/Compat;->id(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)I

    move-result v1

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    .line 162
    :goto_0
    if-eqz v0, :cond_0

    invoke-virtual {v0}, Landroid/widget/TextView;->getText()Ljava/lang/CharSequence;

    move-result-object v0

    invoke-interface {v0}, Ljava/lang/CharSequence;->toString()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {v0}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v0

    invoke-virtual {p0}, Landroid/widget/TextView;->getText()Ljava/lang/CharSequence;

    move-result-object v1

    invoke-interface {v1}, Ljava/lang/CharSequence;->toString()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/String;->trim()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/String;->equalsIgnoreCase(Ljava/lang/String;)Z

    move-result v0

    if-eqz v0, :cond_0

    const/4 v0, 0x1

    .line 163
    :goto_1
    if-eqz v0, :cond_1

    const/16 v0, 0x8

    :goto_2
    invoke-virtual {p0, v0}, Landroid/widget/TextView;->setVisibility(I)V

    .line 164
    return-void

    :cond_0
    move v0, v2

    .line 162
    goto :goto_1

    :cond_1
    move v0, v2

    .line 163
    goto :goto_2

    :cond_2
    move-object v0, v1

    goto :goto_0
.end method

.method public static exec(Landroid/os/AsyncTask;[Ljava/lang/Object;)Landroid/os/AsyncTask;
    .locals 1

    .prologue
    .line 50
    sget-object v0, Landroid/os/AsyncTask;->THREAD_POOL_EXECUTOR:Ljava/util/concurrent/Executor;

    invoke-virtual {p0, v0, p1}, Landroid/os/AsyncTask;->executeOnExecutor(Ljava/util/concurrent/Executor;[Ljava/lang/Object;)Landroid/os/AsyncTask;

    move-result-object v0

    return-object v0
.end method

.method private static findTabMenuItem(Landroid/app/TabActivity;Landroid/view/View;Ljava/lang/String;)Landroid/view/MenuItem;
    .locals 2

    .prologue
    .line 282
    invoke-virtual {p0}, Landroid/app/TabActivity;->getCurrentActivity()Landroid/app/Activity;

    move-result-object v0

    .line 283
    if-nez v0, :cond_0

    .line 284
    const/4 v0, 0x0

    .line 289
    :goto_0
    return-object v0

    .line 286
    :cond_0
    new-instance v1, Landroid/widget/PopupMenu;

    invoke-direct {v1, p0, p1}, Landroid/widget/PopupMenu;-><init>(Landroid/content/Context;Landroid/view/View;)V

    invoke-virtual {v1}, Landroid/widget/PopupMenu;->getMenu()Landroid/view/Menu;

    move-result-object v1

    .line 287
    invoke-virtual {v0, v1}, Landroid/app/Activity;->onCreateOptionsMenu(Landroid/view/Menu;)Z

    .line 288
    invoke-virtual {v0, v1}, Landroid/app/Activity;->onPrepareOptionsMenu(Landroid/view/Menu;)Z

    .line 289
    const-string v0, "id"

    invoke-static {p0, v0, p2}, Lolive/app/Compat;->id(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)I

    move-result v0

    invoke-interface {v1, v0}, Landroid/view/Menu;->findItem(I)Landroid/view/MenuItem;

    move-result-object v0

    goto :goto_0
.end method

.method private static id(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)I
    .locals 2

    .prologue
    .line 98
    invoke-virtual {p0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    invoke-virtual {p0}, Landroid/content/Context;->getPackageName()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, p2, p1, v1}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result v0

    return v0
.end method

.method public static ipAddress(Landroid/net/wifi/WifiInfo;)I
    .locals 5

    .prologue
    const/4 v1, 0x0

    .line 107
    if-eqz p0, :cond_0

    invoke-virtual {p0}, Landroid/net/wifi/WifiInfo;->getIpAddress()I

    move-result v0

    if-eqz v0, :cond_0

    .line 108
    invoke-virtual {p0}, Landroid/net/wifi/WifiInfo;->getIpAddress()I

    move-result v0

    .line 125
    :goto_0
    return v0

    .line 111
    :cond_0
    :try_start_0
    invoke-static {}, Ljava/net/NetworkInterface;->getNetworkInterfaces()Ljava/util/Enumeration;

    move-result-object v0

    invoke-static {v0}, Ljava/util/Collections;->list(Ljava/util/Enumeration;)Ljava/util/ArrayList;

    move-result-object v0

    invoke-virtual {v0}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object v2

    :cond_1
    invoke-interface {v2}, Ljava/util/Iterator;->hasNext()Z

    move-result v0

    if-eqz v0, :cond_3

    invoke-interface {v2}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/net/NetworkInterface;

    .line 112
    invoke-virtual {v0}, Ljava/net/NetworkInterface;->isUp()Z

    move-result v3

    if-eqz v3, :cond_1

    invoke-virtual {v0}, Ljava/net/NetworkInterface;->isLoopback()Z

    move-result v3

    if-nez v3, :cond_1

    .line 115
    invoke-virtual {v0}, Ljava/net/NetworkInterface;->getInetAddresses()Ljava/util/Enumeration;

    move-result-object v0

    invoke-static {v0}, Ljava/util/Collections;->list(Ljava/util/Enumeration;)Ljava/util/ArrayList;

    move-result-object v0

    invoke-virtual {v0}, Ljava/util/ArrayList;->iterator()Ljava/util/Iterator;

    move-result-object v3

    :cond_2
    invoke-interface {v3}, Ljava/util/Iterator;->hasNext()Z

    move-result v0

    if-eqz v0, :cond_1

    invoke-interface {v3}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Ljava/net/InetAddress;

    .line 116
    instance-of v4, v0, Ljava/net/Inet4Address;

    if-eqz v4, :cond_2

    .line 117
    invoke-virtual {v0}, Ljava/net/InetAddress;->getAddress()[B

    move-result-object v0

    .line 118
    const/4 v2, 0x0

    aget-byte v2, v0, v2

    and-int/lit16 v2, v2, 0xff

    const/4 v3, 0x1

    aget-byte v3, v0, v3

    and-int/lit16 v3, v3, 0xff

    shl-int/lit8 v3, v3, 0x8

    or-int/2addr v2, v3

    const/4 v3, 0x2

    aget-byte v3, v0, v3

    and-int/lit16 v3, v3, 0xff

    shl-int/lit8 v3, v3, 0x10

    or-int/2addr v2, v3

    const/4 v3, 0x3

    aget-byte v0, v0, v3
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    and-int/lit16 v0, v0, 0xff

    shl-int/lit8 v0, v0, 0x18

    or-int/2addr v0, v2

    goto :goto_0

    .line 122
    :catch_0
    move-exception v0

    .line 123
    invoke-virtual {v0}, Ljava/lang/Exception;->printStackTrace()V

    :cond_3
    move v0, v1

    .line 125
    goto :goto_0
.end method

.method public static keepProgressStyle(Landroid/widget/ProgressBar;Landroid/graphics/drawable/Drawable;)V
    .locals 0

    .prologue
    .line 358
    return-void
.end method

.method public static declared-synchronized multicast(Landroid/content/Context;Z)V
    .locals 3

    .prologue
    .line 56
    const-class v1, Lolive/app/Compat;

    monitor-enter v1

    if-eqz p1, :cond_2

    .line 57
    :try_start_0
    sget-object v0, Lolive/app/Compat;->multicastLock:Landroid/net/wifi/WifiManager$MulticastLock;

    if-nez v0, :cond_0

    .line 58
    invoke-virtual {p0}, Landroid/content/Context;->getApplicationContext()Landroid/content/Context;

    move-result-object v0

    const-string v2, "wifi"

    invoke-virtual {v0, v2}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/net/wifi/WifiManager;

    .line 59
    const-string v2, "olive-ssdp"

    invoke-virtual {v0, v2}, Landroid/net/wifi/WifiManager;->createMulticastLock(Ljava/lang/String;)Landroid/net/wifi/WifiManager$MulticastLock;

    move-result-object v0

    sput-object v0, Lolive/app/Compat;->multicastLock:Landroid/net/wifi/WifiManager$MulticastLock;

    .line 60
    sget-object v0, Lolive/app/Compat;->multicastLock:Landroid/net/wifi/WifiManager$MulticastLock;

    const/4 v2, 0x0

    invoke-virtual {v0, v2}, Landroid/net/wifi/WifiManager$MulticastLock;->setReferenceCounted(Z)V

    .line 62
    :cond_0
    sget-object v0, Lolive/app/Compat;->multicastLock:Landroid/net/wifi/WifiManager$MulticastLock;

    invoke-virtual {v0}, Landroid/net/wifi/WifiManager$MulticastLock;->acquire()V
    :try_end_0
    .catch Ljava/lang/RuntimeException; {:try_start_0 .. :try_end_0} :catch_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 69
    :cond_1
    :goto_0
    monitor-exit v1

    return-void

    .line 63
    :cond_2
    :try_start_1
    sget-object v0, Lolive/app/Compat;->multicastLock:Landroid/net/wifi/WifiManager$MulticastLock;

    if-eqz v0, :cond_1

    sget-object v0, Lolive/app/Compat;->multicastLock:Landroid/net/wifi/WifiManager$MulticastLock;

    invoke-virtual {v0}, Landroid/net/wifi/WifiManager$MulticastLock;->isHeld()Z

    move-result v0

    if-eqz v0, :cond_1

    .line 64
    sget-object v0, Lolive/app/Compat;->multicastLock:Landroid/net/wifi/WifiManager$MulticastLock;

    invoke-virtual {v0}, Landroid/net/wifi/WifiManager$MulticastLock;->release()V
    :try_end_1
    .catch Ljava/lang/RuntimeException; {:try_start_1 .. :try_end_1} :catch_0
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    goto :goto_0

    .line 66
    :catch_0
    move-exception v0

    .line 67
    :try_start_2
    invoke-virtual {v0}, Ljava/lang/RuntimeException;->printStackTrace()V
    :try_end_2
    .catchall {:try_start_2 .. :try_end_2} :catchall_0

    goto :goto_0

    .line 56
    :catchall_0
    move-exception v0

    monitor-exit v1

    throw v0
.end method

.method public static playIconKey(Landroid/graphics/drawable/Drawable;)I
    .locals 8

    .prologue
    const/16 v4, 0x200

    const/4 v1, 0x1

    const/4 v2, 0x0

    .line 318
    if-nez p0, :cond_0

    .line 331
    :goto_0
    return v2

    .line 321
    :cond_0
    invoke-virtual {p0}, Landroid/graphics/drawable/Drawable;->getConstantState()Landroid/graphics/drawable/Drawable$ConstantState;

    move-result-object v0

    if-nez v0, :cond_1

    .line 323
    :goto_1
    invoke-virtual {p0}, Landroid/graphics/drawable/Drawable;->getIntrinsicWidth()I

    move-result v0

    if-lez v0, :cond_2

    invoke-virtual {p0}, Landroid/graphics/drawable/Drawable;->getIntrinsicWidth()I

    move-result v0

    :goto_2
    invoke-static {v0, v4}, Ljava/lang/Math;->min(II)I

    move-result v0

    invoke-static {v1, v0}, Ljava/lang/Math;->max(II)I

    move-result v3

    .line 324
    invoke-virtual {p0}, Landroid/graphics/drawable/Drawable;->getIntrinsicHeight()I

    move-result v0

    if-lez v0, :cond_3

    invoke-virtual {p0}, Landroid/graphics/drawable/Drawable;->getIntrinsicHeight()I

    move-result v0

    :goto_3
    invoke-static {v0, v4}, Ljava/lang/Math;->min(II)I

    move-result v0

    invoke-static {v1, v0}, Ljava/lang/Math;->max(II)I

    move-result v7

    .line 325
    sget-object v0, Landroid/graphics/Bitmap$Config;->ARGB_8888:Landroid/graphics/Bitmap$Config;

    invoke-static {v3, v7, v0}, Landroid/graphics/Bitmap;->createBitmap(IILandroid/graphics/Bitmap$Config;)Landroid/graphics/Bitmap;

    move-result-object v0

    .line 326
    invoke-virtual {p0, v2, v2, v3, v7}, Landroid/graphics/drawable/Drawable;->setBounds(IIII)V

    .line 327
    new-instance v1, Landroid/graphics/Canvas;

    invoke-direct {v1, v0}, Landroid/graphics/Canvas;-><init>(Landroid/graphics/Bitmap;)V

    invoke-virtual {p0, v1}, Landroid/graphics/drawable/Drawable;->draw(Landroid/graphics/Canvas;)V

    .line 328
    mul-int v1, v3, v7

    new-array v1, v1, [I

    move v4, v2

    move v5, v2

    move v6, v3

    .line 329
    invoke-virtual/range {v0 .. v7}, Landroid/graphics/Bitmap;->getPixels([IIIIIII)V

    .line 330
    invoke-virtual {v0}, Landroid/graphics/Bitmap;->recycle()V

    .line 331
    invoke-static {v1}, Ljava/util/Arrays;->hashCode([I)I

    move-result v2

    goto :goto_0

    .line 321
    :cond_1
    invoke-virtual {p0}, Landroid/graphics/drawable/Drawable;->getConstantState()Landroid/graphics/drawable/Drawable$ConstantState;

    move-result-object v0

    invoke-virtual {v0}, Landroid/graphics/drawable/Drawable$ConstantState;->newDrawable()Landroid/graphics/drawable/Drawable;

    move-result-object v0

    invoke-virtual {v0}, Landroid/graphics/drawable/Drawable;->mutate()Landroid/graphics/drawable/Drawable;

    move-result-object p0

    goto :goto_1

    .line 323
    :cond_2
    const/16 v0, 0x60

    goto :goto_2

    .line 324
    :cond_3
    const/16 v0, 0x48

    goto :goto_3
.end method

.method private static selectRailItem(Landroid/app/TabActivity;I)Landroid/view/View;
    .locals 6

    .prologue
    const/4 v4, 0x0

    const/4 v2, 0x0

    .line 270
    const-string v0, "id"

    const-string v1, "navRail"

    invoke-static {p0, v0, v1}, Lolive/app/Compat;->id(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)I

    move-result v0

    invoke-virtual {p0, v0}, Landroid/app/TabActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    .line 271
    instance-of v1, v0, Landroid/view/ViewGroup;

    if-nez v1, :cond_0

    .line 278
    :goto_0
    return-object v4

    .line 274
    :cond_0
    check-cast v0, Landroid/view/ViewGroup;

    move v1, v2

    .line 275
    :goto_1
    invoke-virtual {v0}, Landroid/view/ViewGroup;->getChildCount()I

    move-result v3

    if-ge v1, v3, :cond_2

    .line 276
    invoke-virtual {v0, v1}, Landroid/view/ViewGroup;->getChildAt(I)Landroid/view/View;

    move-result-object v5

    if-ne v1, p1, :cond_1

    const/4 v3, 0x1

    :goto_2
    invoke-virtual {v5, v3}, Landroid/view/View;->setSelected(Z)V

    .line 275
    add-int/lit8 v1, v1, 0x1

    goto :goto_1

    :cond_1
    move v3, v2

    .line 276
    goto :goto_2

    .line 278
    :cond_2
    if-ltz p1, :cond_3

    invoke-virtual {v0}, Landroid/view/ViewGroup;->getChildCount()I

    move-result v1

    if-ge p1, v1, :cond_3

    invoke-virtual {v0, p1}, Landroid/view/ViewGroup;->getChildAt(I)Landroid/view/View;

    move-result-object v0

    :goto_3
    move-object v4, v0

    goto :goto_0

    :cond_3
    move-object v0, v4

    goto :goto_3
.end method

.method public static setPlayIcon(Landroid/widget/ImageButton;I)V
    .locals 2

    .prologue
    .line 305
    invoke-virtual {p0}, Landroid/widget/ImageButton;->getContext()Landroid/content/Context;

    move-result-object v0

    invoke-virtual {v0, p1}, Landroid/content/Context;->getDrawable(I)Landroid/graphics/drawable/Drawable;

    move-result-object v0

    .line 306
    invoke-static {v0}, Lolive/app/Compat;->playIconKey(Landroid/graphics/drawable/Drawable;)I

    move-result v1

    invoke-static {p0, v0, v1}, Lolive/app/Compat;->swapPlayIcon(Landroid/widget/ImageButton;Landroid/graphics/drawable/Drawable;I)V

    .line 307
    return-void
.end method

.method public static setPlayIcon(Landroid/widget/ImageButton;Landroid/graphics/drawable/Drawable;)V
    .locals 1

    .prologue
    .line 310
    invoke-static {p1}, Lolive/app/Compat;->playIconKey(Landroid/graphics/drawable/Drawable;)I

    move-result v0

    invoke-static {p0, p1, v0}, Lolive/app/Compat;->swapPlayIcon(Landroid/widget/ImageButton;Landroid/graphics/drawable/Drawable;I)V

    .line 311
    return-void
.end method

.method public static setupShell(Landroid/app/TabActivity;)V
    .locals 9

    .prologue
    const/4 v8, 0x1

    const/4 v7, 0x0

    .line 186
    invoke-virtual {p0}, Landroid/app/TabActivity;->getTabHost()Landroid/widget/TabHost;

    move-result-object v1

    .line 187
    const-string v0, "id"

    const-string v2, "screenTitle"

    invoke-static {p0, v0, v2}, Lolive/app/Compat;->id(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)I

    move-result v0

    invoke-virtual {p0, v0}, Landroid/app/TabActivity;->findViewById(I)Landroid/view/View;

    move-result-object v3

    check-cast v3, Landroid/widget/TextView;

    .line 188
    const-string v0, "id"

    const-string v2, "searchButton"

    invoke-static {p0, v0, v2}, Lolive/app/Compat;->id(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)I

    move-result v0

    invoke-virtual {p0, v0}, Landroid/app/TabActivity;->findViewById(I)Landroid/view/View;

    move-result-object v4

    .line 189
    const-string v0, "id"

    const-string v2, "shuffleButton"

    invoke-static {p0, v0, v2}, Lolive/app/Compat;->id(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)I

    move-result v0

    invoke-virtual {p0, v0}, Landroid/app/TabActivity;->findViewById(I)Landroid/view/View;

    move-result-object v5

    .line 190
    new-array v6, v8, [Z

    aput-boolean v7, v6, v7

    .line 191
    invoke-static {p0}, Lolive/app/Compat;->buildRail(Landroid/app/TabActivity;)V

    .line 192
    new-instance v0, Lolive/app/Compat$2;

    move-object v2, p0

    invoke-direct/range {v0 .. v6}, Lolive/app/Compat$2;-><init>(Landroid/widget/TabHost;Landroid/app/TabActivity;Landroid/widget/TextView;Landroid/view/View;Landroid/view/View;[Z)V

    .line 224
    invoke-virtual {v1, v0}, Landroid/widget/TabHost;->setOnTabChangedListener(Landroid/widget/TabHost$OnTabChangeListener;)V

    .line 225
    invoke-virtual {v1}, Landroid/widget/TabHost;->getCurrentTabTag()Ljava/lang/String;

    move-result-object v1

    invoke-interface {v0, v1}, Landroid/widget/TabHost$OnTabChangeListener;->onTabChanged(Ljava/lang/String;)V

    .line 226
    aput-boolean v8, v6, v7

    .line 227
    return-void
.end method

.method public static showTabMenu(Landroid/app/TabActivity;Landroid/view/View;)V
    .locals 3

    .prologue
    .line 77
    invoke-virtual {p0}, Landroid/app/TabActivity;->getCurrentActivity()Landroid/app/Activity;

    move-result-object v0

    .line 78
    if-nez v0, :cond_0

    .line 95
    :goto_0
    return-void

    .line 81
    :cond_0
    new-instance v1, Landroid/widget/PopupMenu;

    invoke-direct {v1, p0, p1}, Landroid/widget/PopupMenu;-><init>(Landroid/content/Context;Landroid/view/View;)V

    .line 82
    invoke-virtual {v1}, Landroid/widget/PopupMenu;->getMenu()Landroid/view/Menu;

    move-result-object v2

    .line 83
    invoke-virtual {v0, v2}, Landroid/app/Activity;->onCreateOptionsMenu(Landroid/view/Menu;)Z

    .line 84
    invoke-virtual {v0, v2}, Landroid/app/Activity;->onPrepareOptionsMenu(Landroid/view/Menu;)Z

    .line 85
    invoke-interface {v2}, Landroid/view/Menu;->hasVisibleItems()Z

    move-result v2

    if-nez v2, :cond_1

    .line 86
    const-string v0, "No options for this tab"

    const/4 v1, 0x0

    invoke-static {p0, v0, v1}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v0

    invoke-virtual {v0}, Landroid/widget/Toast;->show()V

    goto :goto_0

    .line 89
    :cond_1
    new-instance v2, Lolive/app/Compat$1;

    invoke-direct {v2, v0}, Lolive/app/Compat$1;-><init>(Landroid/app/Activity;)V

    invoke-virtual {v1, v2}, Landroid/widget/PopupMenu;->setOnMenuItemClickListener(Landroid/widget/PopupMenu$OnMenuItemClickListener;)V

    .line 94
    invoke-virtual {v1}, Landroid/widget/PopupMenu;->show()V

    goto :goto_0
.end method

.method public static skipDiscovery(Landroid/app/Activity;)Z
    .locals 4

    .prologue
    const/4 v1, 0x0

    .line 134
    const/4 v2, 0x0

    .line 136
    :try_start_0
    invoke-virtual {p0}, Landroid/app/Activity;->getApplicationContext()Landroid/content/Context;

    move-result-object v0

    const-string v3, "wifi"

    invoke-virtual {v0, v3}, Landroid/content/Context;->getSystemService(Ljava/lang/String;)Ljava/lang/Object;

    move-result-object v0

    check-cast v0, Landroid/net/wifi/WifiManager;

    .line 137
    invoke-virtual {v0}, Landroid/net/wifi/WifiManager;->getConnectionInfo()Landroid/net/wifi/WifiInfo;
    :try_end_0
    .catch Ljava/lang/RuntimeException; {:try_start_0 .. :try_end_0} :catch_0

    move-result-object v0

    .line 140
    :goto_0
    if-eqz v0, :cond_0

    invoke-virtual {v0}, Landroid/net/wifi/WifiInfo;->getIpAddress()I

    move-result v0

    if-eqz v0, :cond_0

    move v0, v1

    .line 152
    :goto_1
    return v0

    .line 138
    :catch_0
    move-exception v0

    move-object v0, v2

    goto :goto_0

    .line 143
    :cond_0
    const-string v0, "id"

    const-string v2, "searchLoadingIcon"

    invoke-static {p0, v0, v2}, Lolive/app/Compat;->id(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)I

    move-result v0

    invoke-virtual {p0, v0}, Landroid/app/Activity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    .line 144
    if-eqz v0, :cond_1

    .line 145
    const/16 v2, 0x8

    invoke-virtual {v0, v2}, Landroid/view/View;->setVisibility(I)V

    .line 147
    :cond_1
    const-string v0, "id"

    const-string v2, "searchDevicesText"

    invoke-static {p0, v0, v2}, Lolive/app/Compat;->id(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)I

    move-result v0

    invoke-virtual {p0, v0}, Landroid/app/Activity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    .line 148
    if-eqz v0, :cond_2

    .line 149
    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setVisibility(I)V

    .line 150
    const-string v1, "Automatic search needs the Olive\'s Wi-Fi. Enter the address above, or use Scan a network."

    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 152
    :cond_2
    const/4 v0, 0x1

    goto :goto_1
.end method

.method private static swapPlayIcon(Landroid/widget/ImageButton;Landroid/graphics/drawable/Drawable;I)V
    .locals 4

    .prologue
    const v3, 0x3f51eb85    # 0.82f

    .line 335
    invoke-virtual {p0}, Landroid/widget/ImageButton;->getContext()Landroid/content/Context;

    move-result-object v0

    const-string v1, "id"

    const-string v2, "olive_play_icon"

    invoke-static {v0, v1, v2}, Lolive/app/Compat;->id(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)I

    move-result v1

    .line 336
    if-nez v1, :cond_2

    const/4 v0, 0x0

    .line 337
    :goto_0
    if-eqz v1, :cond_0

    .line 338
    invoke-static {p2}, Ljava/lang/Integer;->valueOf(I)Ljava/lang/Integer;

    move-result-object v2

    invoke-virtual {p0, v1, v2}, Landroid/widget/ImageButton;->setTag(ILjava/lang/Object;)V

    .line 340
    :cond_0
    if-eqz v0, :cond_1

    check-cast v0, Ljava/lang/Integer;

    invoke-virtual {v0}, Ljava/lang/Integer;->intValue()I

    move-result v0

    if-eq v0, p2, :cond_1

    invoke-virtual {p0}, Landroid/widget/ImageButton;->isAttachedToWindow()Z

    move-result v0

    if-nez v0, :cond_3

    .line 341
    :cond_1
    invoke-virtual {p0, p1}, Landroid/widget/ImageButton;->setImageDrawable(Landroid/graphics/drawable/Drawable;)V

    .line 351
    :goto_1
    return-void

    .line 336
    :cond_2
    invoke-virtual {p0, v1}, Landroid/widget/ImageButton;->getTag(I)Ljava/lang/Object;

    move-result-object v0

    goto :goto_0

    .line 344
    :cond_3
    invoke-virtual {p0}, Landroid/widget/ImageButton;->animate()Landroid/view/ViewPropertyAnimator;

    move-result-object v0

    invoke-virtual {v0}, Landroid/view/ViewPropertyAnimator;->cancel()V

    .line 345
    invoke-virtual {p0}, Landroid/widget/ImageButton;->animate()Landroid/view/ViewPropertyAnimator;

    move-result-object v0

    invoke-virtual {v0, v3}, Landroid/view/ViewPropertyAnimator;->scaleX(F)Landroid/view/ViewPropertyAnimator;

    move-result-object v0

    invoke-virtual {v0, v3}, Landroid/view/ViewPropertyAnimator;->scaleY(F)Landroid/view/ViewPropertyAnimator;

    move-result-object v0

    const-wide/16 v2, 0x5a

    invoke-virtual {v0, v2, v3}, Landroid/view/ViewPropertyAnimator;->setDuration(J)Landroid/view/ViewPropertyAnimator;

    move-result-object v0

    sget-object v1, Lolive/app/Compat;->EMPHASIZED:Landroid/view/animation/Interpolator;

    invoke-virtual {v0, v1}, Landroid/view/ViewPropertyAnimator;->setInterpolator(Landroid/animation/TimeInterpolator;)Landroid/view/ViewPropertyAnimator;

    move-result-object v0

    new-instance v1, Lolive/app/Compat$4;

    invoke-direct {v1, p0, p1}, Lolive/app/Compat$4;-><init>(Landroid/widget/ImageButton;Landroid/graphics/drawable/Drawable;)V

    invoke-virtual {v0, v1}, Landroid/view/ViewPropertyAnimator;->withEndAction(Ljava/lang/Runnable;)Landroid/view/ViewPropertyAnimator;

    move-result-object v0

    .line 350
    invoke-virtual {v0}, Landroid/view/ViewPropertyAnimator;->start()V

    goto :goto_1
.end method

.method public static tabAction(Landroid/app/TabActivity;Landroid/view/View;Ljava/lang/String;)V
    .locals 2

    .prologue
    .line 294
    invoke-static {p0, p1, p2}, Lolive/app/Compat;->findTabMenuItem(Landroid/app/TabActivity;Landroid/view/View;Ljava/lang/String;)Landroid/view/MenuItem;

    move-result-object v0

    .line 295
    if-eqz v0, :cond_0

    .line 296
    invoke-virtual {p0}, Landroid/app/TabActivity;->getCurrentActivity()Landroid/app/Activity;

    move-result-object v1

    invoke-virtual {v1, v0}, Landroid/app/Activity;->onOptionsItemSelected(Landroid/view/MenuItem;)Z

    .line 298
    :cond_0
    return-void
.end method

.method public static tabIndicator(Landroid/app/Activity;Landroid/widget/TabHost$TabSpec;Ljava/lang/CharSequence;Landroid/graphics/drawable/Drawable;)Landroid/widget/TabHost$TabSpec;
    .locals 4

    .prologue
    .line 168
    invoke-virtual {p0}, Landroid/app/Activity;->getLayoutInflater()Landroid/view/LayoutInflater;

    move-result-object v0

    const-string v1, "layout"

    const-string v2, "olive_tab"

    invoke-static {p0, v1, v2}, Lolive/app/Compat;->id(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)I

    move-result v1

    const/4 v2, 0x0

    invoke-virtual {v0, v1, v2}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;)Landroid/view/View;

    move-result-object v1

    .line 169
    const-string v0, "drawable"

    new-instance v2, Ljava/lang/StringBuilder;

    invoke-direct {v2}, Ljava/lang/StringBuilder;-><init>()V

    const-string v3, "ic_nav_"

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-interface {p2}, Ljava/lang/CharSequence;->toString()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v3}, Ljava/lang/String;->toLowerCase()Ljava/lang/String;

    move-result-object v3

    invoke-virtual {v2, v3}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    move-result-object v2

    invoke-virtual {v2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    invoke-static {p0, v0, v2}, Lolive/app/Compat;->id(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)I

    move-result v2

    .line 170
    const-string v0, "id"

    const-string v3, "tabIcon"

    invoke-static {p0, v0, v3}, Lolive/app/Compat;->id(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)I

    move-result v0

    invoke-virtual {v1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ImageView;

    .line 171
    if-eqz v2, :cond_0

    .line 172
    invoke-virtual {v0, v2}, Landroid/widget/ImageView;->setImageResource(I)V

    .line 176
    :goto_0
    const-string v0, "id"

    const-string v2, "tabLabel"

    invoke-static {p0, v0, v2}, Lolive/app/Compat;->id(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)I

    move-result v0

    invoke-virtual {v1, v0}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    invoke-virtual {v0, p2}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 177
    invoke-virtual {v1, p2}, Landroid/view/View;->setContentDescription(Ljava/lang/CharSequence;)V

    .line 178
    invoke-virtual {p1, v1}, Landroid/widget/TabHost$TabSpec;->setIndicator(Landroid/view/View;)Landroid/widget/TabHost$TabSpec;

    move-result-object v0

    return-object v0

    .line 174
    :cond_0
    invoke-virtual {v0, p3}, Landroid/widget/ImageView;->setImageDrawable(Landroid/graphics/drawable/Drawable;)V

    goto :goto_0
.end method

.method public static themeTextColor(Landroid/widget/TextView;I)V
    .locals 0

    .prologue
    .line 355
    return-void
.end method

.method private static watchLists(Landroid/view/View;)V
    .locals 4

    .prologue
    const/4 v1, 0x0

    .line 372
    instance-of v0, p0, Landroid/widget/ListView;

    if-eqz v0, :cond_3

    .line 373
    check-cast p0, Landroid/widget/ListView;

    .line 374
    invoke-virtual {p0}, Landroid/widget/ListView;->getContext()Landroid/content/Context;

    move-result-object v0

    const-string v2, "id"

    const-string v3, "olive_list_watch"

    invoke-static {v0, v2, v3}, Lolive/app/Compat;->id(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)I

    move-result v2

    .line 375
    if-eqz v2, :cond_0

    invoke-virtual {p0, v2}, Landroid/widget/ListView;->getTag(I)Ljava/lang/Object;

    move-result-object v0

    if-nez v0, :cond_0

    invoke-virtual {p0}, Landroid/widget/ListView;->getLayoutAnimation()Landroid/view/animation/LayoutAnimationController;

    move-result-object v0

    if-nez v0, :cond_1

    .line 396
    :cond_0
    :goto_0
    return-void

    .line 378
    :cond_1
    const/4 v0, 0x1

    new-array v3, v0, [I

    invoke-virtual {p0}, Landroid/widget/ListView;->getAdapter()Landroid/widget/ListAdapter;

    move-result-object v0

    if-nez v0, :cond_2

    move v0, v1

    :goto_1
    aput v0, v3, v1

    .line 379
    sget-object v0, Ljava/lang/Boolean;->TRUE:Ljava/lang/Boolean;

    invoke-virtual {p0, v2, v0}, Landroid/widget/ListView;->setTag(ILjava/lang/Object;)V

    .line 380
    invoke-virtual {p0}, Landroid/widget/ListView;->getViewTreeObserver()Landroid/view/ViewTreeObserver;

    move-result-object v0

    new-instance v1, Lolive/app/Compat$5;

    invoke-direct {v1, p0, v3}, Lolive/app/Compat$5;-><init>(Landroid/widget/ListView;[I)V

    invoke-virtual {v0, v1}, Landroid/view/ViewTreeObserver;->addOnGlobalLayoutListener(Landroid/view/ViewTreeObserver$OnGlobalLayoutListener;)V

    goto :goto_0

    .line 378
    :cond_2
    invoke-virtual {p0}, Landroid/widget/ListView;->getAdapter()Landroid/widget/ListAdapter;

    move-result-object v0

    invoke-interface {v0}, Landroid/widget/ListAdapter;->getCount()I

    move-result v0

    goto :goto_1

    .line 390
    :cond_3
    instance-of v0, p0, Landroid/view/ViewGroup;

    if-eqz v0, :cond_0

    .line 391
    check-cast p0, Landroid/view/ViewGroup;

    .line 392
    :goto_2
    invoke-virtual {p0}, Landroid/view/ViewGroup;->getChildCount()I

    move-result v0

    if-ge v1, v0, :cond_0

    .line 393
    invoke-virtual {p0, v1}, Landroid/view/ViewGroup;->getChildAt(I)Landroid/view/View;

    move-result-object v0

    invoke-static {v0}, Lolive/app/Compat;->watchLists(Landroid/view/View;)V

    .line 392
    add-int/lit8 v1, v1, 0x1

    goto :goto_2
.end method
