import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JList;
import java.awt.Button;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FrameDashboard extends JFrame {

	private JPanel contentPane;
	private Image main_logo = new ImageIcon(FrameDashboard.class.getResource("res/UMSlogo.png")).getImage().getScaledInstance(90,90,Image.SCALE_SMOOTH);
	private Image classroom_logo = new ImageIcon(FrameDashboard.class.getResource("res/ClassroomLogo.png")).getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH);
	private Image department_logo = new ImageIcon(FrameDashboard.class.getResource("res/DepartmentLogo.png")).getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH);
	private Image instructor_logo = new ImageIcon(FrameDashboard.class.getResource("res/InstructorLogo.png")).getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH);
	private Image prereq_logo = new ImageIcon(FrameDashboard.class.getResource("res/PrereqLogo.png")).getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH);
	private Image section_logo = new ImageIcon(FrameDashboard.class.getResource("res/SectionLogo.png")).getImage().getScaledInstance(40,40,Image.SCALE_SMOOTH);
	private Image student_logo = new ImageIcon(FrameDashboard.class.getResource("res/StudentLogo.png")).getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH);
	private Image takes_logo = new ImageIcon(FrameDashboard.class.getResource("res/TakesLogo.png")).getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH);
	private Image teaches_logo = new ImageIcon(FrameDashboard.class.getResource("res/TeachesLogo.png")).getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH);
	private Image timeslot_logo = new ImageIcon(FrameDashboard.class.getResource("res/TimeslotLogo.png")).getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH);
	private Image course_logo = new ImageIcon(FrameDashboard.class.getResource("res/CourseLogo.png")).getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH);
	private Image advisor_logo = new ImageIcon(FrameDashboard.class.getResource("res/AdvisorLogo.png")).getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH);
	private Image credits_logo = new ImageIcon(FrameDashboard.class.getResource("res/CreditsLogo.png")).getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH);
	
	/**
	
	 * Launch the application.
	 */
	
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameDashboard frame = new FrameDashboard();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	

	/**
	 * Create the frame.
	 */
	public FrameDashboard() {
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		setUndecorated(true);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(128, 128, 128));
		contentPane.setBorder(new LineBorder(new Color(70, 130, 180), 2));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panelMenu = new JPanel();
		panelMenu.setBackground(new Color(70, 130, 180));
		panelMenu.setBounds(0, 0, 600, 430);
		contentPane.add(panelMenu);
		panelMenu.setLayout(null);
		
		JLabel lblNewUniLogo = new JLabel("University Management Database");
		lblNewUniLogo.setFont(new Font("Arial Narrow", Font.BOLD, 24));
		lblNewUniLogo.setBounds(0, 0, 600, 88);
		lblNewUniLogo.setIcon(new ImageIcon(main_logo));
		panelMenu.add(lblNewUniLogo);
		
		JPanel panelclassroom = new JPanel();
		panelclassroom.setBackground(new Color(70, 130, 180));
		panelclassroom.setBounds(10, 148, 186, 53);
		panelMenu.add(panelclassroom);
		panelclassroom.setLayout(null);
		
		JButton btnNewButton = new JButton("Classroom");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("static-access")
			@Override
			public void mouseClicked(MouseEvent arg0) {
				FrameDashboard.this.dispose();
				FrameClassroom fclass = new FrameClassroom();
				fclass.ClassroomScreen();
				
			}
		});
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setBackground(new Color(0, 0, 0));
		btnNewButton.setFont(new Font("Bell MT", Font.BOLD, 13));
		btnNewButton.setBounds(77, 11, 109, 31);
		panelclassroom.add(btnNewButton);
		
		JLabel lblNewclasslogo = new JLabel("");
		lblNewclasslogo.setBackground(new Color(70, 130, 180));
		lblNewclasslogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewclasslogo.setBounds(14, 0, 53, 53);
		lblNewclasslogo.setIcon(new ImageIcon(classroom_logo));
		panelclassroom.add(lblNewclasslogo);
		
		
		JPanel paneldepartment = new JPanel();
		paneldepartment.setBackground(new Color(70, 130, 180));
		paneldepartment.setBounds(10, 214, 186, 49);
		panelMenu.add(paneldepartment);
		paneldepartment.setLayout(null);
		
		JButton btnDepartment = new JButton("Department\r\n");
		btnDepartment.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("static-access")
			@Override
			public void mouseClicked(MouseEvent arg0) {
				FrameDashboard.this.dispose();
				FrameDepartment fdept = new FrameDepartment();
				fdept.DepartmentScreen();
				
			}
		});
		btnDepartment.setForeground(Color.WHITE);
		btnDepartment.setFont(new Font("Bell MT", Font.BOLD, 13));
		btnDepartment.setBackground(Color.BLACK);
		btnDepartment.setBounds(77, 11, 109, 31);
		paneldepartment.add(btnDepartment);
		
		JLabel lblNewDepartment = new JLabel("");
		lblNewDepartment.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewDepartment.setBackground(new Color(70, 130, 180));
		lblNewDepartment.setBounds(14, 0, 53, 53);
		lblNewDepartment.setIcon(new ImageIcon(department_logo));
		paneldepartment.add(lblNewDepartment);
		
		JPanel panelcourse = new JPanel();
		panelcourse.setBackground(new Color(70, 130, 180));
		panelcourse.setBounds(10, 274, 186, 51);
		panelMenu.add(panelcourse);
		panelcourse.setLayout(null);
		
		JButton btnCourse = new JButton("Course\r\n");
		btnCourse.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("static-access")
			@Override
			public void mouseClicked(MouseEvent arg0) {
				FrameDashboard.this.dispose();
				FrameCourse fcourse = new FrameCourse();
				fcourse.CourseScreen();
				
			}
		});
		btnCourse.setForeground(Color.WHITE);
		btnCourse.setFont(new Font("Bell MT", Font.BOLD, 13));
		btnCourse.setBackground(Color.BLACK);
		btnCourse.setBounds(77, 11, 109, 31);
		panelcourse.add(btnCourse);
		
		JLabel lblNewcCourse = new JLabel("");
		lblNewcCourse.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewcCourse.setBackground(new Color(70, 130, 180));
		lblNewcCourse.setBounds(14, 0, 53, 53);
		lblNewcCourse.setIcon(new ImageIcon(course_logo));
		panelcourse.add(lblNewcCourse);
		
		JPanel panelinstructor = new JPanel();
		panelinstructor.setBackground(new Color(70, 130, 180));
		panelinstructor.setBounds(10, 336, 186, 53);
		panelMenu.add(panelinstructor);
		panelinstructor.setLayout(null);
		JButton btnInstructor = new JButton("Instructor");
		btnInstructor.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("static-access")
			@Override
			public void mouseClicked(MouseEvent e) {
				FrameDashboard.this.dispose();
				FrameInstructor finstructor = new FrameInstructor();
				finstructor.InstructorScreen();
			}
		});
		btnInstructor.setForeground(Color.WHITE);
		btnInstructor.setFont(new Font("Bell MT", Font.BOLD, 13));
		btnInstructor.setBackground(Color.BLACK);
		btnInstructor.setBounds(77, 11, 109, 31);
		panelinstructor.add(btnInstructor);
		
		JLabel lblNewInstructor = new JLabel("");
		lblNewInstructor.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewInstructor.setBackground(new Color(70, 130, 180));
		lblNewInstructor.setBounds(14, 0, 53, 53);
		lblNewInstructor.setIcon(new ImageIcon(instructor_logo));
		panelinstructor.add(lblNewInstructor);
		
		JPanel panelsection = new JPanel();
		panelsection.setBackground(new Color(70, 130, 180));
		panelsection.setBounds(206, 148, 186, 53);
		panelMenu.add(panelsection);
		panelsection.setLayout(null);
		
		JButton btnNewSection = new JButton("Section\r\n");
		btnNewSection.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				FrameDashboard.this.dispose();
				FrameSection fsection = new FrameSection();
				fsection.SectionScreen();
			}
		});
		btnNewSection.setForeground(Color.WHITE);
		btnNewSection.setFont(new Font("Bell MT", Font.BOLD, 13));
		btnNewSection.setBackground(Color.BLACK);
		btnNewSection.setBounds(77, 11, 109, 31);
		panelsection.add(btnNewSection);
		
		JLabel lblNewsection = new JLabel("");
		lblNewsection.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewsection.setBackground(new Color(70, 130, 180));
		lblNewsection.setBounds(10, 0, 53, 53);
		lblNewsection.setIcon(new ImageIcon(section_logo));
		panelsection.add(lblNewsection);
		
		JPanel panelteaches = new JPanel();
		panelteaches.setBackground(new Color(70, 130, 180));
		panelteaches.setBounds(206, 210, 186, 53);
		panelMenu.add(panelteaches);
		panelteaches.setLayout(null);
		
		JButton btnNewTeaches = new JButton("Teaches\r\n");
		btnNewTeaches.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("static-access")
			@Override
			public void mouseClicked(MouseEvent arg0) {
				FrameDashboard.this.dispose();
				FrameTeaches fteaches = new FrameTeaches();
				fteaches.TeachesScreen();
				
			}
		});
		btnNewTeaches.setForeground(Color.WHITE);
		btnNewTeaches.setFont(new Font("Bell MT", Font.BOLD, 13));
		btnNewTeaches.setBackground(Color.BLACK);
		btnNewTeaches.setBounds(77, 11, 109, 31);
		panelteaches.add(btnNewTeaches);
		
		JLabel lblNewTeaches = new JLabel("");
		lblNewTeaches.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewTeaches.setBackground(new Color(70, 130, 180));
		lblNewTeaches.setBounds(14, 0, 53, 53);
		lblNewTeaches.setIcon(new ImageIcon(teaches_logo));
		panelteaches.add(lblNewTeaches);
		
		JPanel panelstudent = new JPanel();
		panelstudent.setBackground(new Color(70, 130, 180));
		panelstudent.setBounds(206, 272, 186, 53);
		panelMenu.add(panelstudent);
		panelstudent.setLayout(null);
		
		JButton btnNewStudent = new JButton("Student\r\n");
		btnNewStudent.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("static-access")
			@Override
			public void mouseClicked(MouseEvent arg0) {
				FrameDashboard.this.dispose();
				FrameStudent fstudent = new FrameStudent();
				fstudent.StudentScreen();
				
			}
		});
		btnNewStudent.setForeground(Color.WHITE);
		btnNewStudent.setFont(new Font("Bell MT", Font.BOLD, 13));
		btnNewStudent.setBackground(Color.BLACK);
		btnNewStudent.setBounds(77, 11, 109, 31);
		panelstudent.add(btnNewStudent);
		
		JLabel lblNewStudent = new JLabel("");
		lblNewStudent.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewStudent.setBackground(new Color(70, 130, 180));
		lblNewStudent.setBounds(14, 0, 53, 53);
		lblNewStudent.setIcon(new ImageIcon(student_logo));
		panelstudent.add(lblNewStudent);
		
		JPanel paneltakes = new JPanel();
		paneltakes.setBackground(new Color(70, 130, 180));
		paneltakes.setBounds(206, 336, 186, 53);
		panelMenu.add(paneltakes);
		paneltakes.setLayout(null);
		
		JButton btnNewTakes = new JButton("Takes\r\n");
		btnNewTakes.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("static-access")
			@Override
			public void mouseClicked(MouseEvent arg0) {
				FrameDashboard.this.dispose();
				FrameTakes ftakes = new FrameTakes();
				ftakes.TakesScreen();
				
			}
		});
		btnNewTakes.setForeground(Color.WHITE);
		btnNewTakes.setFont(new Font("Bell MT", Font.BOLD, 13));
		btnNewTakes.setBackground(Color.BLACK);
		btnNewTakes.setBounds(77, 11, 109, 31);
		paneltakes.add(btnNewTakes);
		
		JLabel lblNewTakes = new JLabel("");
		lblNewTakes.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewTakes.setBackground(new Color(70, 130, 180));
		lblNewTakes.setBounds(14, 0, 53, 53);
		lblNewTakes.setIcon(new ImageIcon(takes_logo));
		paneltakes.add(lblNewTakes);
		
		JPanel paneladvisor = new JPanel();
		paneladvisor.setBackground(new Color(70, 130, 180));
		paneladvisor.setBounds(402, 148, 186, 53);
		panelMenu.add(paneladvisor);
		paneladvisor.setLayout(null);
		
		JButton btnNewAdvisor = new JButton("Advisor\r\n");
		btnNewAdvisor.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("static-access")
			@Override
			public void mouseClicked(MouseEvent arg0) {
				FrameDashboard.this.dispose();
				FrameAdvisor fadvisor = new FrameAdvisor();
				fadvisor.AdvisorScreen();
				
			}
		});
		btnNewAdvisor.setForeground(Color.WHITE);
		btnNewAdvisor.setFont(new Font("Bell MT", Font.BOLD, 13));
		btnNewAdvisor.setBackground(Color.BLACK);
		btnNewAdvisor.setBounds(77, 11, 109, 31);
		paneladvisor.add(btnNewAdvisor);
		
		JLabel lblNewAdvisor = new JLabel("");
		lblNewAdvisor.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewAdvisor.setBackground(new Color(70, 130, 180));
		lblNewAdvisor.setBounds(14, 0, 53, 53);
		lblNewAdvisor.setIcon(new ImageIcon(advisor_logo));
		paneladvisor.add(lblNewAdvisor);
		
		JPanel paneltimeslot = new JPanel();
		paneltimeslot.setBackground(new Color(70, 130, 180));
		paneltimeslot.setBounds(402, 210, 186, 53);
		panelMenu.add(paneltimeslot);
		paneltimeslot.setLayout(null);
		
		JButton btnNewTimeslot = new JButton("Time_slot");
		btnNewTimeslot.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("static-access")
			@Override
			public void mouseClicked(MouseEvent arg0) {
				FrameDashboard.this.dispose();
				FrameTimeslot fcourse = new FrameTimeslot();
				fcourse.TimeslotScreen();
				
			}
		});
		btnNewTimeslot.setForeground(Color.WHITE);
		btnNewTimeslot.setFont(new Font("Bell MT", Font.BOLD, 13));
		btnNewTimeslot.setBackground(Color.BLACK);
		btnNewTimeslot.setBounds(77, 11, 109, 31);
		paneltimeslot.add(btnNewTimeslot);
		
		JLabel lblNewTimeSlot = new JLabel("");
		lblNewTimeSlot.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewTimeSlot.setBackground(new Color(70, 130, 180));
		lblNewTimeSlot.setBounds(14, 0, 53, 53);
		lblNewTimeSlot.setIcon(new ImageIcon(timeslot_logo));
		paneltimeslot.add(lblNewTimeSlot);
		
		JPanel panelprereq = new JPanel();
		panelprereq.setBackground(new Color(70, 130, 180));
		panelprereq.setBounds(402, 272, 186, 53);
		panelMenu.add(panelprereq);
		panelprereq.setLayout(null);
		
		JButton btnNewPrereq = new JButton("Prereq");
		btnNewPrereq.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("static-access")
			@Override
			public void mouseClicked(MouseEvent arg0) {
				FrameDashboard.this.dispose();
				FramePrereq fprereq = new FramePrereq();
				fprereq.PrereqScreen();
				
			}
		});
		btnNewPrereq.setForeground(Color.WHITE);
		btnNewPrereq.setFont(new Font("Bell MT", Font.BOLD, 13));
		btnNewPrereq.setBackground(Color.BLACK);
		btnNewPrereq.setBounds(77, 11, 109, 31);
		panelprereq.add(btnNewPrereq);
		
		JLabel lblNewPrereq = new JLabel("");
		lblNewPrereq.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewPrereq.setBackground(new Color(70, 130, 180));
		lblNewPrereq.setBounds(14, 0, 53, 53);
		lblNewPrereq.setIcon(new ImageIcon(prereq_logo));
		panelprereq.add(lblNewPrereq);
		
		JPanel panel_credits = new JPanel();
		panel_credits.setBackground(new Color(70, 130, 180));
		panel_credits.setBounds(402, 336, 186, 53);
		panelMenu.add(panel_credits);
		panel_credits.setLayout(null);
		
		JButton btnNewCredits = new JButton("Credits");
		btnNewCredits.setForeground(Color.WHITE);
		btnNewCredits.setFont(new Font("Bell MT", Font.BOLD, 13));
		btnNewCredits.setBackground(Color.BLACK);
		btnNewCredits.setBounds(77, 11, 109, 31);
		panel_credits.add(btnNewCredits);
		
		JLabel lblNewCredits = new JLabel("");
		lblNewCredits.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewCredits.setBackground(new Color(70, 130, 180));
		lblNewCredits.setBounds(14, 0, 53, 53);
		lblNewCredits.setIcon(new ImageIcon(credits_logo));
		panel_credits.add(lblNewCredits);
		
		final JLabel lblX = new JLabel("X");
		lblX.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				if(JOptionPane.showConfirmDialog(null , "are you sure you want to close ?","Confirmation",JOptionPane.YES_NO_CANCEL_OPTION)==0) {
					FrameDashboard.this.dispose();
					
				}
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {
				lblX.setForeground(Color.RED);
			}
			@Override
			public void mouseExited(MouseEvent arg0) {
				lblX.setForeground(Color.WHITE);
			}
		});
		lblX.setForeground(Color.WHITE);
		lblX.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		lblX.setHorizontalAlignment(SwingConstants.CENTER);
		lblX.setBounds(580, 0, 20, 20);
		panelMenu.add(lblX);
	}
}
