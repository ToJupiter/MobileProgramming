# Thông tin sinh viên <br>
Họ và tên: Phan Hoàng Hải <br>
MSSV: 20225715 <br>

# Bài tập 1: <br>
+ Người dùng nhập thông tin vào edit Họ tên và MSSV. Nhấn nút Add thì thêm mới phần tử với dữ liệu tương ứng vào danh sách. <br>
+ Người dùng nhấn nút xóa trên phần tử nào thì phần tử đó bị xóa khỏi danh sách. <br>
+ Người dùng nhấn vào một phần tử trong danh sách thì thông tin của phần tử đó được hiển thị trong 2 ô EditText, khi đó người dùng có thể cập nhật họ tên của phần tử và nhấn nút Update. Thông tin sẽ được cập nhật vào danh sách. <br>

## TỔNG QUAN ỨNG DỤNG <br>
*   Ứng dụng cho phép **CRUD** (Create, Read, Update, Delete) thông tin sinh viên. <br>
*   Kiến trúc đơn giản: **MainActivity** quản lý toàn bộ logic và hiển thị. <br>
*   Sử dụng **RecyclerView** để hiển thị danh sách sinh viên một cách hiệu quả. <br>

## GIAO DIỆN <br>

### 1. `activity_main.xml` (Màn hình chính) <br>
*   Sử dụng **LinearLayout** với orientation="vertical" để sắp xếp các thành phần theo chiều dọc. <br>
*   Gồm 2 **EditText** để nhập MSSV và Họ tên. <br>
*   Có 2 **Button** (Add/Update) trong một **LinearLayout** ngang: <br>
    *   Button "Add" luôn hiển thị để thêm mới. <br>
    *   Button "Update" ban đầu ẩn (visibility="gone"), chỉ hiện khi người dùng chọn một item để sửa. <br>
*   **RecyclerView** chiếm không gian còn lại (layout_weight="1") để hiển thị danh sách. <br>
*   Có một header danh sách với nền màu xám, sử dụng **textStyle="bold"** để làm nổi bật. <br>

### 2. `item_student.xml` (Layout cho mỗi item trong danh sách) <br>
*   Sử dụng **LinearLayout** ngang để hiển thị thông tin sinh viên và nút xóa. <br>
*   Hai **TextView** hiển thị MSSV và Họ tên, chiều rộng bằng nhau (layout_weight="1"). <br>
*   **Button "Xóa"** có layout_width="wrap_content" để vừa với chữ. <br>
*   Có thuộc tính **android:background="?android:attr/selectableItemBackground"** tạo hiệu ứng phản hồi khi chạm. <br>

## LOGIC & XỬ LÝ DỮ LIỆU <br>

### 1. `Student.kt` (Data Class) <br>
*   Là một **data class** - tự động sinh ra các hàm `equals()`, `hashCode()`, `toString()`. <br>
*   Chứa 2 thuộc tính: `studentId` (String) và `fullName` (String). <br>
*   Đóng vai trò là **Model** trong kiến trúc. <br>

### 2. `StudentAdapter.kt` (RecyclerView Adapter) <br>
*   Kế thừa từ **RecyclerView.Adapter<StudentAdapter.StudentViewHolder>**. <br>
*   **StudentViewHolder**: <br>
    *   Chứa references đến các view trong `item_student.xml`. <br>
    *   Sử dụng **view binding** thủ công thông qua `findViewById`. <br>
*   **onCreateViewHolder()**: <br>
    *   "Inflate" layout `item_student.xml` để tạo view cho mỗi item. <br>
*   **onBindViewHolder()**: <br>
    *   Gắn dữ liệu từ `studentList[position]` vào các TextView. <br>
    *   Xử lý sự kiện: <br>
        *   **Click vào item**: gọi lambda `onItemClick` để chuyển sang chế độ sửa. <br>
        *   **Click nút "Xóa"**: gọi lambda `onDeleteClick` để xóa item. <br>
*   **updateList()**: <br>
    *   Cập nhật toàn bộ danh sách mới và thông báo cho RecyclerView bằng `notifyDataSetChanged()`. <br>

### 3. `MainActivity.kt` (Controller & View) <br>
*   **Biến quan trọng**: <br>
    *   `studentList`: MutableList<Student> - lưu trữ dữ liệu sinh viên. <br>
    *   `currentEditPosition`: Theo dõi vị trí item đang được sửa. <br>
*   **initViews()**: Tìm các view từ layout bằng findViewById. <br>
*   **setupRecyclerView()**: <br>
    *   Khởi tạo **StudentAdapter** với 2 lambda function xử lý sự kiện click. <br>
    *   Thiết lập **LinearLayoutManager** (layout dọc) cho RecyclerView. <br>
*   **setupClickListeners()**: Gán sự kiện click cho các button. <br>
*   **addStudent()**: <br>
    *   Lấy dữ liệu từ EditText, kiểm tra không rỗng. <br>
    *   Tạo đối tượng **Student** mới và thêm vào `studentList`. <br>
    *   Thông báo cho adapter bằng `notifyItemInserted()`. <br>
*   **updateStudent()**: <br>
    *   Cập nhật `studentList` tại vị trí `currentEditPosition`. <br>
    *   Thông báo cho adapter bằng `notifyItemChanged()`. <br>
    *   Reset trạng thái: ẩn nút Update, hiện nút Add. <br>

## CƠ CHẾ HOẠT ĐỘNG <br>

### Luồng Thêm Mới: <br>
1.  User nhập MSSV + Họ tên → Click "Add" <br>
2.  `addStudent()` tạo đối tượng → thêm vào list → adapter cập nhật RecyclerView <br>

### Luồng Chỉnh Sửa: <br>
1.  User click vào item trong danh sách <br>
2.  Adapter gọi `onItemClick` → MainActivity hiển thị dữ liệu lên EditText và hiện nút "Update" <br>
3.  User sửa dữ liệu → Click "Update" → `updateStudent()` cập nhật list → adapter cập nhật RecyclerView <br>

### Luồng Xóa: <br>
1.  User click nút "Xóa" trên item <br>
2.  Adapter gọi `onDeleteClick` → MainActivity xóa item khỏi list → adapter cập nhật RecyclerView bằng `notifyItemRemoved()` <br>

### Demo: <br>
1. Danh sách: <br>

![ds](image.png) <br>

2. Khi bấm vào 1 phần tử: <br>
![ds2](image-1.png) <br>

3. Xóa phần tử: <br>
![ds3](image-2.png) <br>

4. Chỉnh sửa: <br>
![ds4](image-3.png) <br>


# Bài tập 2: Ứng dụng minh họa giao diện danh sách tương tự với ứng dụng Gmail <br>

## TỔNG QUAN ỨNG DỤNG <br>
*   Ứng dụng inbox email với giao diện giống Gmail <br>
*   Hiển thị danh sách hội thoại với RecyclerView <br>
*   Hỗ trợ click toàn item hoặc click riêng avatar để chọn <br>

## GIAO DIỆN NGƯỜI DÙNG <br>

### activity_main.xml <br>
*   **CoordinatorLayout** với **AppBarLayout** chứa Toolbar tiêu đề "Inbox" <br>
*   **LinearLayout** dọc có app:layout_behavior để scroll cùng AppBar <br>
*   TextView hướng dẫn và RecyclerView hiển thị danh sách hội thoại <br>

### item_conversation.xml <br>
*   **ConstraintLayout** với ImageView avatar hình tròn (background shape oval) <br>
*   Các TextView hiển thị senderName (bold), subject (màu xanh), preview (màu xám) <br>
*   Timestamp căn end và divider ngang phân cách items <br>

## XỬ LÝ LOGIC & DỮ LIỆU <br>

### Conversation.kt <br>
*   **Data class** với các thuộc tính: senderId, senderName, subject, preview, timestamp, isUnread <br>

### ConversationAdapter.kt <br>
*   **RecyclerView.Adapter** xử lý 2 loại click: toàn item và click avatar <br>
*   **onBindViewHolder** set Typeface.BOLD cho senderName nếu isUnread = true <br>
*   **updateConversations** cập nhật danh sách với notifyDataSetChanged <br>

### MainActivity.kt <br>
*   **setupToolbar** thiết lập Toolbar với title "Inbox" <br>
*   **setupRecyclerView** khởi tạo adapter với 2 lambda xử lý click <br>
*   **loadSampleData** tạo dữ liệu mẫu với 5 hội thoại, 2 unread <br>

## CƠ CHẾ HOẠT ĐỘNG <br>
*   **Click item**: Hiện Toast "Opened conversation với [senderName]" <br>
*   **Click avatar**: Hiện Toast "Selected conversation với [senderName]"   <br>
*   **Unread message**: Hiển thị senderName với chữ đậm (Typeface.BOLD) <br>
*   **Scroll**: RecyclerView scroll cùng Toolbar nhờ appbar_scrolling_view_behavior <br>

### Demo: <br>
1. Email view: <br>
![gmail](image-4.png) <br>

2. Khi bấm vào 1 email có thông báo đã bấm vào: <br>
![gmail2](image-5.png) <br>



# Bài tập 3: Ứng dụng minh họa giao diện danh sách lồng nhau tương tự ứng dụng Play Store <br>
## TỔNG QUAN ỨNG DỤNG <br>
*   App store UI với multiple sections và nested RecyclerViews <br>
*   TabLayout với 3 tabs: "SUNO" và 2 star icons <br>
*   Hỗ trợ cả hiển thị ngang và dọc cho các loại content khác nhau <br>

## GIAO DIỆN NGƯỜI DÙNG <br>

### activity_main.xml <br>
*   **CoordinatorLayout** với **AppBarLayout** chứa **TabLayout** màu trắng <br>
*   Tab indicator màu xanh, text màu xám khi unselected, đen khi selected <br>
*   Main **RecyclerView** scroll cùng TabLayout <br>

### Các Item Layouts <br>
*   **item_section_header.xml**: TextView bold cho section title <br>
*   **item_divider.xml**: View 1dp màu xám phân cách sections   <br>
*   **item_app_vertical.xml**: ConstraintLayout hiển thị app info chi tiết (title, genres, rating, size) <br>
*   **item_app_horizontal_card.xml**: CardView 120dp chứa icon, title, subtitle - layout cho horizontal scrolling <br>
*   **item_app_list_section.xml**: Chứa section title + nested RecyclerView cho apps <br>

## XỬ LÝ LOGIC & DỮ LIỆU <br>

### Models.kt <br>
*   **StoreItem**: Data class với displayType (VERTICAL_LIST/HORIZONTAL_CARD) <br>
*   **MainListItem**: Sealed class cho 3 loại items: SectionHeader, AppListSection, Divider <br>

### Adapter System <br>
*   **MainAdapter**: RecyclerView.Adapter chính xử lý 3 view types <br>
*   **AppSectionViewHolder**: Quản lý nested RecyclerView với LinearLayoutManager ngang/dọc <br>
*   **AppVerticalAdapter**: Hiển thị apps dạng list chi tiết <br>
*   **AppHorizontalAdapter**: Hiển thị apps dạng card ngang <br>

## CƠ CHẾ HOẠT ĐỘNG <br>
*   **MainAdapter** detect item type → inflate layout tương ứng <br>
*   **AppListSectionItem** chứa nested RecyclerView với orientation khác nhau <br>
*   **Sponsored apps**: Hiển thị dọc với đầy đủ genres, rating, size <br>
*   **Recommended apps**: Hiển thị ngang dạng card compact với icon, title, subtitle <br>
*   Click app → trigger lambda function hiển thị Toast <br>

### Demo: <br>
1. Giao diện hàng dọc: <br>
![play1](image-6.png) <br>

2. Giao diện thanh cuộn ngang: <br>
![play2](image-7.png) <br>
