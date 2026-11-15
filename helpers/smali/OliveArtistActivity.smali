.class public Lolive/app/OliveArtistActivity;
.super Landroid/app/Activity;
.source "OliveArtistActivity.java"


# instance fields
.field private art:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList",
            "<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field

.field private ids:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList",
            "<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field

.field private titles:Ljava/util/ArrayList;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "Ljava/util/ArrayList",
            "<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation
.end field


# direct methods
.method public constructor <init>()V
    .locals 0

    .prologue
    .line 21
    invoke-direct {p0}, Landroid/app/Activity;-><init>()V

    return-void
.end method

.method static synthetic access$000(Lolive/app/OliveArtistActivity;)Ljava/util/ArrayList;
    .locals 1

    .prologue
    .line 21
    iget-object v0, p0, Lolive/app/OliveArtistActivity;->ids:Ljava/util/ArrayList;

    return-object v0
.end method

.method static synthetic access$100(Lolive/app/OliveArtistActivity;)Ljava/util/ArrayList;
    .locals 1

    .prologue
    .line 21
    iget-object v0, p0, Lolive/app/OliveArtistActivity;->titles:Ljava/util/ArrayList;

    return-object v0
.end method

.method static synthetic access$200(Lolive/app/OliveArtistActivity;Ljava/lang/String;Ljava/lang/String;)I
    .locals 1

    .prologue
    .line 21
    invoke-direct {p0, p1, p2}, Lolive/app/OliveArtistActivity;->id(Ljava/lang/String;Ljava/lang/String;)I

    move-result v0

    return v0
.end method

.method static synthetic access$300(Lolive/app/OliveArtistActivity;)Ljava/util/ArrayList;
    .locals 1

    .prologue
    .line 21
    iget-object v0, p0, Lolive/app/OliveArtistActivity;->art:Ljava/util/ArrayList;

    return-object v0
.end method

.method private id(Ljava/lang/String;Ljava/lang/String;)I
    .locals 2

    .prologue
    .line 109
    invoke-virtual {p0}, Lolive/app/OliveArtistActivity;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    invoke-virtual {p0}, Lolive/app/OliveArtistActivity;->getPackageName()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, p2, p1, v1}, Landroid/content/res/Resources;->getIdentifier(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result v0

    return v0
.end method

.method private static list(Ljava/util/ArrayList;)Ljava/util/ArrayList;
    .locals 0
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/ArrayList",
            "<",
            "Ljava/lang/String;",
            ">;)",
            "Ljava/util/ArrayList",
            "<",
            "Ljava/lang/String;",
            ">;"
        }
    .end annotation

    .prologue
    .line 105
    if-eqz p0, :cond_0

    :goto_0
    return-object p0

    :cond_0
    new-instance p0, Ljava/util/ArrayList;

    invoke-direct {p0}, Ljava/util/ArrayList;-><init>()V

    goto :goto_0
.end method


# virtual methods
.method protected onCreate(Landroid/os/Bundle;)V
    .locals 3

    .prologue
    .line 28
    invoke-super {p0, p1}, Landroid/app/Activity;->onCreate(Landroid/os/Bundle;)V

    .line 29
    const-string v0, "layout"

    const-string v1, "listview"

    invoke-direct {p0, v0, v1}, Lolive/app/OliveArtistActivity;->id(Ljava/lang/String;Ljava/lang/String;)I

    move-result v0

    invoke-virtual {p0, v0}, Lolive/app/OliveArtistActivity;->setContentView(I)V

    .line 30
    invoke-virtual {p0}, Lolive/app/OliveArtistActivity;->getIntent()Landroid/content/Intent;

    move-result-object v0

    .line 31
    const-string v1, "olive.artist"

    invoke-virtual {v0, v1}, Landroid/content/Intent;->getStringExtra(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    .line 32
    const-string v2, "olive.albumIds"

    invoke-virtual {v0, v2}, Landroid/content/Intent;->getStringArrayListExtra(Ljava/lang/String;)Ljava/util/ArrayList;

    move-result-object v2

    invoke-static {v2}, Lolive/app/OliveArtistActivity;->list(Ljava/util/ArrayList;)Ljava/util/ArrayList;

    move-result-object v2

    iput-object v2, p0, Lolive/app/OliveArtistActivity;->ids:Ljava/util/ArrayList;

    .line 33
    const-string v2, "olive.albumTitles"

    invoke-virtual {v0, v2}, Landroid/content/Intent;->getStringArrayListExtra(Ljava/lang/String;)Ljava/util/ArrayList;

    move-result-object v2

    invoke-static {v2}, Lolive/app/OliveArtistActivity;->list(Ljava/util/ArrayList;)Ljava/util/ArrayList;

    move-result-object v2

    iput-object v2, p0, Lolive/app/OliveArtistActivity;->titles:Ljava/util/ArrayList;

    .line 34
    const-string v2, "olive.albumArt"

    invoke-virtual {v0, v2}, Landroid/content/Intent;->getStringArrayListExtra(Ljava/lang/String;)Ljava/util/ArrayList;

    move-result-object v0

    invoke-static {v0}, Lolive/app/OliveArtistActivity;->list(Ljava/util/ArrayList;)Ljava/util/ArrayList;

    move-result-object v0

    iput-object v0, p0, Lolive/app/OliveArtistActivity;->art:Ljava/util/ArrayList;

    .line 36
    const-string v0, "id"

    const-string v2, "ListViewTitle"

    invoke-direct {p0, v0, v2}, Lolive/app/OliveArtistActivity;->id(Ljava/lang/String;Ljava/lang/String;)I

    move-result v0

    invoke-virtual {p0, v0}, Lolive/app/OliveArtistActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 37
    const-string v0, "id"

    const-string v1, "shuffleAll"

    invoke-direct {p0, v0, v1}, Lolive/app/OliveArtistActivity;->id(Ljava/lang/String;Ljava/lang/String;)I

    move-result v0

    invoke-virtual {p0, v0}, Lolive/app/OliveArtistActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    const/16 v1, 0x8

    invoke-virtual {v0, v1}, Landroid/view/View;->setVisibility(I)V

    .line 38
    new-instance v0, Lolive/app/OliveArtistActivity$1;

    invoke-direct {v0, p0}, Lolive/app/OliveArtistActivity$1;-><init>(Lolive/app/OliveArtistActivity;)V

    .line 43
    const-string v1, "id"

    const-string v2, "back"

    invoke-direct {p0, v1, v2}, Lolive/app/OliveArtistActivity;->id(Ljava/lang/String;Ljava/lang/String;)I

    move-result v1

    invoke-virtual {p0, v1}, Lolive/app/OliveArtistActivity;->findViewById(I)Landroid/view/View;

    move-result-object v1

    invoke-virtual {v1, v0}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 44
    const-string v1, "id"

    const-string v2, "backImg"

    invoke-direct {p0, v1, v2}, Lolive/app/OliveArtistActivity;->id(Ljava/lang/String;Ljava/lang/String;)I

    move-result v1

    invoke-virtual {p0, v1}, Lolive/app/OliveArtistActivity;->findViewById(I)Landroid/view/View;

    move-result-object v1

    invoke-virtual {v1, v0}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 45
    new-instance v0, Lolive/app/OliveArtistActivity$2;

    invoke-direct {v0, p0}, Lolive/app/OliveArtistActivity$2;-><init>(Lolive/app/OliveArtistActivity;)V

    .line 52
    const-string v1, "id"

    const-string v2, "home"

    invoke-direct {p0, v1, v2}, Lolive/app/OliveArtistActivity;->id(Ljava/lang/String;Ljava/lang/String;)I

    move-result v1

    invoke-virtual {p0, v1}, Lolive/app/OliveArtistActivity;->findViewById(I)Landroid/view/View;

    move-result-object v1

    invoke-virtual {v1, v0}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 53
    const-string v1, "id"

    const-string v2, "homeImg"

    invoke-direct {p0, v1, v2}, Lolive/app/OliveArtistActivity;->id(Ljava/lang/String;Ljava/lang/String;)I

    move-result v1

    invoke-virtual {p0, v1}, Lolive/app/OliveArtistActivity;->findViewById(I)Landroid/view/View;

    move-result-object v1

    invoke-virtual {v1, v0}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 54
    const-string v0, "id"

    const-string v1, "playerbar"

    invoke-direct {p0, v0, v1}, Lolive/app/OliveArtistActivity;->id(Ljava/lang/String;Ljava/lang/String;)I

    move-result v0

    invoke-virtual {p0, v0}, Lolive/app/OliveArtistActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    new-instance v1, Lolive/app/OliveArtistActivity$3;

    invoke-direct {v1, p0}, Lolive/app/OliveArtistActivity$3;-><init>(Lolive/app/OliveArtistActivity;)V

    invoke-virtual {v0, v1}, Landroid/view/View;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 60
    const-string v0, "id"

    const-string v1, "ListView"

    invoke-direct {p0, v0, v1}, Lolive/app/OliveArtistActivity;->id(Ljava/lang/String;Ljava/lang/String;)I

    move-result v0

    invoke-virtual {p0, v0}, Lolive/app/OliveArtistActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/ListView;

    .line 61
    new-instance v1, Lolive/app/OliveArtistActivity$4;

    invoke-direct {v1, p0}, Lolive/app/OliveArtistActivity$4;-><init>(Lolive/app/OliveArtistActivity;)V

    invoke-virtual {v0, v1}, Landroid/widget/ListView;->setAdapter(Landroid/widget/ListAdapter;)V

    .line 84
    new-instance v1, Lolive/app/OliveArtistActivity$5;

    invoke-direct {v1, p0}, Lolive/app/OliveArtistActivity$5;-><init>(Lolive/app/OliveArtistActivity;)V

    invoke-virtual {v0, v1}, Landroid/widget/ListView;->setOnItemClickListener(Landroid/widget/AdapterView$OnItemClickListener;)V

    .line 93
    return-void
.end method

.method protected onResume()V
    .locals 4

    .prologue
    .line 97
    invoke-super {p0}, Landroid/app/Activity;->onResume()V

    .line 99
    const-string v0, "Olive_Settings"

    const/4 v1, 0x0

    invoke-virtual {p0, v0, v1}, Lolive/app/OliveArtistActivity;->getSharedPreferences(Ljava/lang/String;I)Landroid/content/SharedPreferences;

    move-result-object v1

    .line 100
    const-string v0, "id"

    const-string v2, "playerTitle"

    invoke-direct {p0, v0, v2}, Lolive/app/OliveArtistActivity;->id(Ljava/lang/String;Ljava/lang/String;)I

    move-result v0

    invoke-virtual {p0, v0}, Lolive/app/OliveArtistActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    const-string v2, "trackTitle"

    const-string v3, ""

    invoke-interface {v1, v2, v3}, Landroid/content/SharedPreferences;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v0, v2}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 101
    const-string v0, "id"

    const-string v2, "playerAlbum"

    invoke-direct {p0, v0, v2}, Lolive/app/OliveArtistActivity;->id(Ljava/lang/String;Ljava/lang/String;)I

    move-result v0

    invoke-virtual {p0, v0}, Lolive/app/OliveArtistActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/TextView;

    const-string v2, "albumTitle"

    const-string v3, ""

    invoke-interface {v1, v2, v3}, Landroid/content/SharedPreferences;->getString(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/widget/TextView;->setText(Ljava/lang/CharSequence;)V

    .line 102
    return-void
.end method
