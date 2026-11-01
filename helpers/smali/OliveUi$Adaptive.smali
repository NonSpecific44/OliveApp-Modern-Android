.class final Lolive/app/OliveUi$Adaptive;
.super Ljava/lang/Object;
.source "OliveUi.java"

# interfaces
.implements Landroid/view/View$OnLayoutChangeListener;


# annotations
.annotation system Ldalvik/annotation/EnclosingClass;
    value = Lolive/app/OliveUi;
.end annotation

.annotation system Ldalvik/annotation/InnerClass;
    accessFlags = 0x18
    name = "Adaptive"
.end annotation


# instance fields
.field private final activity:Landroid/app/Activity;

.field private lastH:I

.field private lastW:I

.field private final panes:Landroid/widget/LinearLayout;


# direct methods
.method private constructor <init>(Landroid/app/Activity;Landroid/widget/LinearLayout;)V
    .locals 0

    .prologue
    .line 313
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 314
    iput-object p1, p0, Lolive/app/OliveUi$Adaptive;->activity:Landroid/app/Activity;

    .line 315
    iput-object p2, p0, Lolive/app/OliveUi$Adaptive;->panes:Landroid/widget/LinearLayout;

    .line 316
    return-void
.end method

.method static attach(Landroid/app/Activity;)V
    .locals 4

    .prologue
    .line 319
    const-string v0, "adaptivePanes"

    invoke-static {p0, v0}, Lolive/app/OliveUi;->id(Landroid/content/Context;Ljava/lang/String;)I

    move-result v0

    invoke-virtual {p0, v0}, Landroid/app/Activity;->findViewById(I)Landroid/view/View;

    move-result-object v1

    .line 320
    instance-of v0, v1, Landroid/widget/LinearLayout;

    if-nez v0, :cond_1

    .line 333
    :cond_0
    :goto_0
    return-void

    .line 323
    :cond_1
    const-string v0, "olive_adaptive"

    invoke-static {p0, v0}, Lolive/app/OliveUi;->id(Landroid/content/Context;Ljava/lang/String;)I

    move-result v2

    .line 324
    invoke-virtual {v1, v2}, Landroid/view/View;->getTag(I)Ljava/lang/Object;

    move-result-object v0

    if-nez v0, :cond_0

    .line 327
    new-instance v3, Lolive/app/OliveUi$Adaptive;

    move-object v0, v1

    check-cast v0, Landroid/widget/LinearLayout;

    invoke-direct {v3, p0, v0}, Lolive/app/OliveUi$Adaptive;-><init>(Landroid/app/Activity;Landroid/widget/LinearLayout;)V

    .line 328
    invoke-virtual {v1, v2, v3}, Landroid/view/View;->setTag(ILjava/lang/Object;)V

    .line 329
    invoke-virtual {v1, v3}, Landroid/view/View;->addOnLayoutChangeListener(Landroid/view/View$OnLayoutChangeListener;)V

    .line 330
    invoke-virtual {v1}, Landroid/view/View;->getWidth()I

    move-result v0

    if-lez v0, :cond_0

    .line 331
    invoke-virtual {v1}, Landroid/view/View;->getWidth()I

    move-result v0

    invoke-virtual {v1}, Landroid/view/View;->getHeight()I

    move-result v1

    invoke-virtual {v3, v0, v1}, Lolive/app/OliveUi$Adaptive;->apply(II)V

    goto :goto_0
.end method


# virtual methods
.method apply(II)V
    .locals 11

    .prologue
    const/4 v1, 0x1

    const/high16 v10, 0x3f800000    # 1.0f

    const/4 v9, -0x1

    const/4 v2, 0x0

    .line 349
    if-lez p1, :cond_0

    if-gtz p2, :cond_1

    .line 381
    :cond_0
    :goto_0
    return-void

    .line 352
    :cond_1
    iput p1, p0, Lolive/app/OliveUi$Adaptive;->lastW:I

    .line 353
    iput p2, p0, Lolive/app/OliveUi$Adaptive;->lastH:I

    .line 354
    iget-object v0, p0, Lolive/app/OliveUi$Adaptive;->panes:Landroid/widget/LinearLayout;

    iget-object v3, p0, Lolive/app/OliveUi$Adaptive;->activity:Landroid/app/Activity;

    const-string v4, "paneArt"

    invoke-static {v3, v4}, Lolive/app/OliveUi;->id(Landroid/content/Context;Ljava/lang/String;)I

    move-result v3

    invoke-virtual {v0, v3}, Landroid/widget/LinearLayout;->findViewById(I)Landroid/view/View;

    move-result-object v3

    .line 355
    iget-object v0, p0, Lolive/app/OliveUi$Adaptive;->panes:Landroid/widget/LinearLayout;

    iget-object v4, p0, Lolive/app/OliveUi$Adaptive;->activity:Landroid/app/Activity;

    const-string v5, "paneInfo"

    invoke-static {v4, v5}, Lolive/app/OliveUi;->id(Landroid/content/Context;Ljava/lang/String;)I

    move-result v4

    invoke-virtual {v0, v4}, Landroid/widget/LinearLayout;->findViewById(I)Landroid/view/View;

    move-result-object v4

    .line 356
    iget-object v0, p0, Lolive/app/OliveUi$Adaptive;->panes:Landroid/widget/LinearLayout;

    iget-object v5, p0, Lolive/app/OliveUi$Adaptive;->activity:Landroid/app/Activity;

    const-string v6, "artwork"

    invoke-static {v5, v6}, Lolive/app/OliveUi;->id(Landroid/content/Context;Ljava/lang/String;)I

    move-result v5

    invoke-virtual {v0, v5}, Landroid/widget/LinearLayout;->findViewById(I)Landroid/view/View;

    move-result-object v5

    .line 357
    if-eqz v3, :cond_0

    if-eqz v4, :cond_0

    if-eqz v5, :cond_0

    .line 360
    int-to-float v0, p1

    int-to-float v6, p2

    const v7, 0x3f933333    # 1.15f

    mul-float/2addr v6, v7

    cmpl-float v0, v0, v6

    if-lez v0, :cond_5

    move v0, v1

    .line 361
    :goto_1
    iget-object v6, p0, Lolive/app/OliveUi$Adaptive;->panes:Landroid/widget/LinearLayout;

    if-eqz v0, :cond_2

    move v1, v2

    :cond_2
    invoke-virtual {v6, v1}, Landroid/widget/LinearLayout;->setOrientation(I)V

    .line 362
    iget-object v6, p0, Lolive/app/OliveUi$Adaptive;->activity:Landroid/app/Activity;

    iget-object v1, p0, Lolive/app/OliveUi$Adaptive;->activity:Landroid/app/Activity;

    invoke-virtual {v1}, Landroid/app/Activity;->getResources()Landroid/content/res/Resources;

    move-result-object v1

    invoke-virtual {v1}, Landroid/content/res/Resources;->getConfiguration()Landroid/content/res/Configuration;

    move-result-object v1

    iget v1, v1, Landroid/content/res/Configuration;->smallestScreenWidthDp:I

    const/16 v7, 0x258

    if-lt v1, v7, :cond_6

    const/high16 v1, 0x44020000    # 520.0f

    :goto_2
    invoke-static {v6, v1}, Lolive/app/OliveUi;->dp(Landroid/content/Context;F)F

    move-result v1

    .line 363
    iget-object v6, p0, Lolive/app/OliveUi$Adaptive;->activity:Landroid/app/Activity;

    const/high16 v7, 0x42400000    # 48.0f

    invoke-static {v6, v7}, Lolive/app/OliveUi;->dp(Landroid/content/Context;F)F

    move-result v6

    .line 365
    if-eqz v0, :cond_7

    .line 366
    new-instance v0, Landroid/widget/LinearLayout$LayoutParams;

    invoke-direct {v0, v2, v9, v10}, Landroid/widget/LinearLayout$LayoutParams;-><init>(IIF)V

    invoke-virtual {v3, v0}, Landroid/view/View;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    .line 367
    new-instance v0, Landroid/widget/LinearLayout$LayoutParams;

    invoke-direct {v0, v2, v9, v10}, Landroid/widget/LinearLayout$LayoutParams;-><init>(IIF)V

    invoke-virtual {v4, v0}, Landroid/view/View;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    .line 368
    int-to-float v0, p1

    const/high16 v2, 0x40000000    # 2.0f

    div-float/2addr v0, v2

    sub-float/2addr v0, v6

    int-to-float v2, p2

    sub-float/2addr v2, v6

    invoke-static {v0, v2}, Ljava/lang/Math;->min(FF)F

    move-result v0

    invoke-static {v1, v0}, Ljava/lang/Math;->min(FF)F

    move-result v0

    float-to-int v0, v0

    .line 374
    :goto_3
    invoke-virtual {v5}, Landroid/view/View;->getLayoutParams()Landroid/view/ViewGroup$LayoutParams;

    move-result-object v1

    .line 375
    iget v2, v1, Landroid/view/ViewGroup$LayoutParams;->width:I

    if-ne v2, v0, :cond_3

    iget v2, v1, Landroid/view/ViewGroup$LayoutParams;->height:I

    if-eq v2, v0, :cond_4

    .line 376
    :cond_3
    iput v0, v1, Landroid/view/ViewGroup$LayoutParams;->width:I

    .line 377
    iput v0, v1, Landroid/view/ViewGroup$LayoutParams;->height:I

    .line 378
    invoke-virtual {v5, v1}, Landroid/view/View;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    .line 380
    :cond_4
    iget-object v0, p0, Lolive/app/OliveUi$Adaptive;->panes:Landroid/widget/LinearLayout;

    invoke-virtual {v0}, Landroid/widget/LinearLayout;->requestLayout()V

    goto/16 :goto_0

    :cond_5
    move v0, v2

    .line 360
    goto :goto_1

    .line 362
    :cond_6
    const/high16 v1, 0x43b40000    # 360.0f

    goto :goto_2

    .line 370
    :cond_7
    new-instance v0, Landroid/widget/LinearLayout$LayoutParams;

    const/4 v7, -0x2

    const/4 v8, 0x0

    invoke-direct {v0, v9, v7, v8}, Landroid/widget/LinearLayout$LayoutParams;-><init>(IIF)V

    invoke-virtual {v3, v0}, Landroid/view/View;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    .line 371
    new-instance v0, Landroid/widget/LinearLayout$LayoutParams;

    invoke-direct {v0, v9, v2, v10}, Landroid/widget/LinearLayout$LayoutParams;-><init>(IIF)V

    invoke-virtual {v4, v0}, Landroid/view/View;->setLayoutParams(Landroid/view/ViewGroup$LayoutParams;)V

    .line 372
    int-to-float v0, p1

    sub-float/2addr v0, v6

    int-to-float v2, p2

    const v3, 0x3ed70a3d    # 0.42f

    mul-float/2addr v2, v3

    invoke-static {v0, v2}, Ljava/lang/Math;->min(FF)F

    move-result v0

    invoke-static {v1, v0}, Ljava/lang/Math;->min(FF)F

    move-result v0

    float-to-int v0, v0

    goto :goto_3
.end method

.method public onLayoutChange(Landroid/view/View;IIIIIIII)V
    .locals 3

    .prologue
    .line 336
    sub-int v0, p4, p2

    .line 337
    sub-int v1, p5, p3

    .line 338
    iget v2, p0, Lolive/app/OliveUi$Adaptive;->lastW:I

    if-ne v0, v2, :cond_0

    iget v2, p0, Lolive/app/OliveUi$Adaptive;->lastH:I

    if-ne v1, v2, :cond_0

    .line 346
    :goto_0
    return-void

    .line 341
    :cond_0
    new-instance v2, Lolive/app/OliveUi$Adaptive$1;

    invoke-direct {v2, p0, v0, v1}, Lolive/app/OliveUi$Adaptive$1;-><init>(Lolive/app/OliveUi$Adaptive;II)V

    invoke-virtual {p1, v2}, Landroid/view/View;->post(Ljava/lang/Runnable;)Z

    goto :goto_0
.end method
