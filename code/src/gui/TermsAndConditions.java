package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.prefs.Preferences;
import javax.swing.*;
public class TermsAndConditions extends JDialog {

    private final JTextArea text;
    private final JCheckBox check;
    private final JButton cancel;
    private final JButton ok;
    private final JPanel panel;
    private final JScrollPane scroller;
    private final JLabel label;
    private Preferences prefs  = Preferences.userRoot();

    public TermsAndConditions(Component parent) {
        this.setSize(600,600);
        this.setVisible(true);
        this.setTitle("Terms And Conditions");
        this.setLayout(new BoxLayout(this.getContentPane() , BoxLayout.Y_AXIS));
        this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        this.setLocationRelativeTo(parent);
        text = new JTextArea();
        text.setEditable(false);
        this.setAutoRequestFocus(true);
        this.setAlwaysOnTop(true);
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        text.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(),
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)   )));
        setText();
        
        scroller = new JScrollPane(text ,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
                ,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        check = new JCheckBox("I agree to the Terms and Conditions");
        panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        label = new JLabel("Thanks For Choosing Us");
        ok = new JButton("Ok");
        cancel = new JButton("cancel");
        ok.setEnabled(false);
        panel.add(ok);
        panel.add(cancel);
        this.add(label);
        this.add(scroller);
        this.add(check);
        this.add(panel);
        
        label.setFont(new Font(Font.SANS_SERIF , Font.PLAIN , 15));
        label.setAlignmentX(TOP_ALIGNMENT);
        check.setAlignmentX(TOP_ALIGNMENT);
        text.setAlignmentX(TOP_ALIGNMENT);
        panel.setAlignmentX(TOP_ALIGNMENT);
        ok.addActionListener((ActionEvent ev)->{
            prefs.putBoolean("termsa", true);
            TermsAndConditions.this.dispose();
            
        });
        cancel.addActionListener((ActionEvent ev)->{
            System.exit(0);
        });
        check.addActionListener((ActionEvent ev)->{
              ok.setEnabled(check.isSelected());
        });
        
        
    }
    
    private void setText(){
        String string = "7. DISCLAIMER OF WARRANTIES AND LIMITATION OF LIABILITY\n" +
                        "7.1 Warranties\n" +
                        "7.1.1 Except as specifically provided in this Agreement, the Software is provided “as is” without warranty of any kind, express or implied, including but not limited to warranties of performance, merchantability, fitness for a particular purpose, accuracy, omissions, completeness, currentness and delays. Customer agrees that outputs from the Software will not, under any circumstances, be considered legal or professional advice and are not meant to replace the experience and sound professional judgment of professional advisors in full knowledge of the circumstances and details of any matter on which advice is sought. \n" +
                        "7.1.2 Supplier warrants to Customer that it holds itself the necessary rights to grant the rights specified in this Agreement and that it has authority to enter into this Agreement with Customer. \n" +
                        "7.1.3 Some systems/software may not be capable of supporting the Software and Customer acknowledges (a) that it has made appropriate investigations into the necessary systems/software required to support Customer’s use of the relevant Software and (b) that performance of that Software may vary with equipment and telecommunications links with which it is used.\n" +
                        "\n" +
                        "\n" +
                        "7.2 Exclusion of liability\n" +
                        "7.2.1 Neither Supplier, its Affiliates nor any licensors of the foregoing make any warranty that access to any Software will be uninterrupted, secure, complete or error free. \n" +
                        "7.2.2 Other than in respect of the warranty given in Clause 7.1.2 and 8.1 Supplier shall not be liable in contract, tort, delict or otherwise for any loss of whatsoever kind howsoever arising suffered in connection with the Software.\n" +
                        "7.2.3 Supplier shall not be liable in contract, tort, delict or otherwise for any loss of revenue, business, anticipated savings or profits, loss of goodwill or data or for any indirect or consequential loss whatsoever, howsoever arising suffered in connection with the Software.\n" +
                        "7.2.4 Without prejudice to the generality of clauses 7.2.1 to 7.2.3, in no event shall Supplier, its Affiliates and/or the licensors of the foregoing be liable to Customer for any claim(s) relating in any way to:\n" +
                        "(a) Customer's inability or failure to perform legal or other research related work or to perform such legal or other research or related work properly or completely, even if assisted by Supplier, its Affiliates and/or licensors of the foregoing or any decision made or action taken by Customer in reliance on the Software; or\n" +
                        "(b) any lost profits (whether direct or indirect) or any consequential, exemplary, incidental, indirect or special damages relating in whole or in part to Customers' rights under this Agreement or use of or inability to use the Software even if Suppliers, its Affiliates and/or licensors of the foregoing have been advised of the possibility of such damages.\n" +
                        "7.2.5 Other than in respect of the warranty given in Clause 7.1.2 and 8.1 Supplier will have no liability whatsoever for any liability of Customer to any third party which might arise.\n" +
                        "7.2.6 Customer shall accept sole responsibility for and Supplier shall not be liable for the use of the Software by Customer, or any User and Customer shall hold Supplier harmless and fully indemnified against any claims, costs, damages, loss and liabilities arising out of any such use.\n" +
                        "7.2.7 Nothing in this Agreement confers or purports to confer on any third party any benefit or any right to enforce any term of this Agreement.\n" +
                        "\n" +
                        "\n" +
                        "7.3 Limitation of Liability\n" +
                        "7.3.1 Other than in respect of the warranty given in Clause 7.1.2 and 8.1, Customer's exclusive remedy and Supplier's, (its Affiliates' and/or licensors of the foregoing entire liability under this Agreement if any, for any claim(s) for damages relating to the Software made against them individually or jointly whether based in contract or negligence shall be limited to the aggregate amount of the Charges paid by Customer relative to the specific aspect of the Software which is the basis of the claim(s) during the 12 month period preceding the event giving rise to such claim.\n" +
                        "7.3.2 None of the terms of this Agreement shall operate to:\n" +
                        "(a) exclude or restrict liability for fraud or for death or personal injury resulting from the negligence of Supplier or its Affiliates or the appointed agents or employees of Supplier or its Affiliates whilst acting in the course of their employment; or\n" +
                        "(b) affect statutory rights where this Agreement is entered into as a consumer transaction. \n" +
                        "7.3.3 Except for claims relating to non-payment of the Charges or improper use of the Software, no claim regardless of form which in any way arises out of this Agreement may be made, nor action based upon such claim brought, by either party to this Agreement more than one year after the basis for the claim becomes known to the party desiring to assert it.\n" +
                        "\n" +
                        "\n" +
                        "7.4 Failures Not Caused by Supplier\n" +
                        "Supplier will not be responsible to the extent that the Software fails to perform due to one or more of the following: (1) the malfunction of software not provided by Supplier (2) the malfunction of hardware, (3) Customer’s negligence or fault, (4) Customer’s failure to follow the instructions set forth in the Documentation, (5) material changes in the operating environment not authorised by Supplier, (6) modifications to or changes in the Software not made or suggested by Supplier or (7) Customer’s failure to implement and maintain a proper and adequate backup and recovery system for the Software and associated files. If Supplier discovers that a failure is caused by one of the above, Supplier reserves the right to charge Customer for its work in investigating such failure. At Customer’s request and at a fee to be agreed upon, Supplier will thereafter assist Customer in resolving such failure. It is Customer’s responsibility to develop and implement a proper and adequate backup and recovery system.\n" +
                        "\n" +
                        "\n" +
                        "7.5 Exclusive Remedies\n" +
                        "The remedies in clauses 7 (Disclaimer of Warranties and Limitation of Liability), 8 (Infringement Claims), 9 (Term and Termination), 11.4 (Remedies) and 12.3 (Remedies) are Customer’s exclusive remedies and are in lieu of all other legal or equitable remedies and all liabilities or obligations on the part of Supplier for damages (except for death and personal injury) arising out of, relating to, or in connection with this Agreement, including, but not limited to, the licensing, delivery, installation, use or performance of the Software or the integration of the Software with other software or hardware.\n" +
                        "\n" +
                        "\n" +
                        "8. INFRINGEMENT CLAIMS\n" +
                        "8.1 Supplier warrants to Customer that no Software to which Customer has subscribed, nor its features infringe any industrial or intellectual property rights of any third party.\n" +
                        "8.2 Customer shall promptly inform Supplier if Customer becomes aware of:\n" +
                        "(a) any unauthorised use of the Software;\n" +
                        "(b) any actual, threatened, or suspected infringement of any intellectual property of Supplier, its Affiliates and/or licensors of the foregoing in the Software which comes to Customer's notice; and \n" +
                        "(c) any claim by any third party coming to its notice that the Software infringes the intellectual property or other rights of any other person.\n" +
                        "8.3 Customer shall at the request and expense of Supplier do all such things as may be reasonably required to assist Supplier in taking or resisting proceedings in relation to any infringement or claim referred to in this clause and in maintaining the validity and enforceability of the intellectual property of Supplier, its Affiliates and/or licensors of the foregoing in the Software. \n" +
                        "8.4 In the event a claim of infringement is made against Supplier or Customer with respect to the Software, Supplier, for the purpose of settling such claim, may, at its option, in respect of such allegedly infringing Software:\n" +
                        "(i) substitute fully equivalent non-infringing software; or\n" +
                        "(ii) modify the Software so that it no longer infringes but remains functionally equivalent.\n" +
                        "If, as a result of such claim, Customer or Supplier is permanently enjoined from using the Software by a final, non-appealable decree from a court of competent jurisdiction, Supplier will take one or both of the actions set forth in (i) and (ii) above or will obtain for Customer at Supplier’s expense the right to continue to use the Software. \n" +
                        "8.5 Supplier’s obligations to Customer pursuant to this clause 8 is contingent upon Supplier being given prompt notice and control of, and detailed information with regard to, any such claim, suit or proceeding. Customer shall have the right to participate at its own cost in the defence of any such claim or action through legal counsel of its choosing. Customer shall not settle any such claim or action without Supplier’s prior written consent.\n" +
                        "8.6 This clause 8 contains Supplier’s entire obligation and the exclusive remedies of Customer with regard to any claimed infringement arising out of or based upon the Software used by Customer.\n" +
                        "\n" +
                        "\n" +
                        "9. TERM AND TERMINATION\n" +
                        "9.1 This Agreement will, once approved by Supplier, commence on the Start Date and shall continue for the Initial Subscription Period and shall then renew for successive Renewal Periods until the end of the final Renewal Period unless earlier termination takes place in accordance with the provisions set out in clause 9.2.\n" +
                        "9.2 This Agreement may be terminated by: \n" +
                        "9.2.1 Customer on written notice to Supplier after receiving notice of an amendment (as permitted under this Agreement) which is materially detrimental to Customer (“Detrimental Amendment”), which for the avoidance of doubt includes an increase in Charges and/or substantial loss of content or functionality in the Software to Customer's detriment and for which no reasonable substitute is provided), which notice shall not take effect until the date on which such amendment or increase comes into effect; or \n" +
                        "9.2.2 either party on written notice to the other if: \n" +
                        "(a) the other commits a material breach of this Agreement, provided that where the breach is capable of being remedied then the defaulting party shall have failed to remedy the same within 30 days of receiving notice specifying the breach and requiring its remedy; or\n" +
                        "(b) the other is adjudicated bankrupt, enters into liquidation or any arrangement or composition with or assignment for the benefit of its creditors or if a trustee or a receiver or administrator or administrative receiver or receiver and manager is appointed against the whole or any part of its assets or business; or\n" +
                        "9.2.3 either party on receipt of written notice by the other of not less than 30 days prior to, but not taking effect until, the expiry of the Initial Subscription Period or current Renewal Period; or\n" +
                        "9.2.4 Supplier, with immediate effect, if any organisation, which Supplier acting reasonably determines to be a Competitor of Supplier acquires Control of Customer.\n" +
                        "9.3 If at any time Supplier for any reason decides to cease general provision of the Software, Supplier may, on providing not less than ninety (90) days’ written notice to Customer, cease to provide any further Maintenance Services (see clause 11). \n" +
                        "9.4 Upon termination for whatsoever reason, if Customer has pre-paid any Charges in respect of Software being terminated or cancelled Supplier’s sole liability to Customer in respect of such termination shall be to refund the pre-paid Charges in respect of that Software for the period following termination to the end of the Term. No such refund shall be required in event of termination for Customer’s breach of this Agreement.\n" +
                        "9.5 Expiry or termination of this Agreement shall be without prejudice to the accrued rights and obligations of the parties.\n" +
                        "\n" +
                        "\n" +
                        "10. GENERAL PROVISIONS\n" +
                        "10.1 Effect of Agreement \n" +
                        "This Agreement (including any applicable ordering document) embodies the entire understanding between the parties with respect to the subject matter of this Agreement and supersedes any and all prior understandings and agreements, oral or written, relating to the subject matter. Furthermore, this Agreement supersedes the terms and conditions of any clickthrough agreement associated with the Software. Such Special Conditions as are agreed between Supplier and Customer shall apply, subject to clauses 10.1.1 and 10.1.2 for the Term.\n" +
                        "10.1.1 Except as otherwise provided in this Agreement, Supplier may amend the terms and conditions of this Agreement (“Amended Terms”) by giving Customer at least 15 days prior written or online notice. Unless Customer is notified to the contrary by Supplier, such Amended Terms shall only apply after the expiry of any Initial Subscription Period, or after the expiry of the current Renewal Period as the case may be. \n" +
                        "10.1.2 Where agreed Special Conditions are affected by Amended Terms, the parties shall enter into good faith negotiations and agree amendments to the Special Conditions to reflect the parties intentions. Where agreement cannot be reached, such Amended Terms may amount to a Detrimental Amendment and clause 9.2.1 may apply.\n" +
                        "10.1.3 Any other amendment must be in writing and signed by both parties.\n" +
                        "\n" +
                        "10.2 Force Majeure \n" +
                        "Supplier shall not be liable for any delay or failure in performing hereunder if caused by factors beyond its reasonable control, such as acts of God, acts of any government, war or other hostility, civil disorder, the elements, fire, explosion, power failure, equipment failure, failure of telecommunications or Internet services, industrial or labour dispute, inability to obtain necessary supplies and the like.\n" +
                        "\n" +
                        "10.3 Notices\n" +
                        "Except as otherwise provided, all notices and correspondence must be given in writing to Supplier at: Online Business Administration, Cheriton House, PO Boc 2000, Andover SP10 9AH or trluki.legalonlinenotices@thomsonreuters.com or such other addresses as may from time to time be notified to Customer in writing; and to Customer at the address set out in the applicable ordering document unless otherwise notified to Supplier in writing.\n" +
                        "\n" +
                        "10.4 Governing Law and Assignment\n" +
                        "This Agreement and all matters arising out of it shall, unless otherwise specified on the applicable ordering document or by Supplier in writing, in all respects be governed by the laws of England and Wales and shall be subject to the non-exclusive jurisdiction of the English courts. However nothing in this clause shall exclude or limit applicable mandatory local law relating to Customer. Supplier may, upon written notice to Customer, assign or transfer this Agreement or any rights and obligations hereunder either to an Affiliate or to a third party successor to all or substantially all of the business, stock or assets of Supplier’s legal information business, in each case, without the prior consent of Customer. Supplier may without the prior written consent of Customer and without notice assign any benefit or transfer, delegate or sub-contract any of their duties and obligations under this Agreement to any third party, provided that in the case of sub-contracting, Supplier shall remain responsible for the performance by its sub-contractors of such obligations under the Agreement. Neither this Agreement nor any part or portion may be assigned, sublicensed or otherwise transferred by Customer without Supplier’s prior written consent. Should any provision of this Agreement be held to be void, invalid, unenforceable or illegal by a court, the validity and enforceability of the other provisions will not be affected thereby. Failure of any party to enforce any provision of this Agreement will not constitute or be construed as a waiver of such provision or of the right to enforce such provision. The headings and captions contained in this Agreement are inserted for convenience only and do not constitute a part of this Agreement.\n" +
                        "\n" +
                        "10.5 Export Laws\n" +
                        "Customer agrees to comply with all relevant export laws and regulations of the United States and other countries (collectively, “Export Laws”) to ensure that no Software or any portion of it is exported, directly or indirectly, in violation of Export Laws, and that no access to the specified services is given by Customer to any embargoed country or their nationals, or any other embargoed/denied persons listed from time to time by the United States or other counties. Supplier will not be liable for default or delay caused by Supplier’s efforts to comply with Export Laws. If Export Laws change after signature of this Agreement and such changes materially inhibit or prohibit Supplier from performing its obligations under this Agreement, Supplier will not be liable for their non-performance and either or both Supplier and Customer will have the right to terminate this Agreement with respect to the applicable Software.\n" +
                        "\n" +
                        "10.6. Survival\n" +
                        "Clauses 5 (Confidential Information), 7 (Disclaimer of Warranties and Limitation of Liability), and 10 (General Provisions) shall survive any termination or expiry of this Agreement.\n" +
                        "\n" +
                        "11. MAINTENANCE TERMS\n" +
                        "11.1 Maintenance & Support Services\n" +
                        "Maintenance & Support Services consist of the following:\n" +
                        "11.1.1 Updates. Supplier may provide Updates to and/or new Versions for the Software to Customer which shall be included in the Charges. Customer will be responsible for installing any such Updates and/or new Versions. However, Supplier will provide technical support for only the most current Version and the immediately preceding Version of the Software.\n" +
                        "11.1.2 Telephone Support. Supplier will provide telephone support for purposes of handling Customer questions relating to the operation of the Software. Telephone support is provided by Supplier’s Customer Training and Support Team see www.westlaw.co.uk for contact details.\n" +
                        "\n" +
                        "11.2 Supplier’s Obligations\n" +
                        "11.2.1 Supplier’s obligations hereunder will extend only to (a) the Updates and Versions of the Software provided to Customer by Supplier; and (b) Software that has not been modified or altered in any way by anyone other than Supplier. \n" +
                        "11.2.2 Maintenance Services will not include services for the items for which Supplier is not responsible set forth in clause 7.4 of this Agreement.\n" +
                        "\n" +
                        "11.3 Customer Obligations.\n" +
                        "11.3.1 Cooperation. Customer shall ensure that Supplier’s personnel are provided with such information under Customer’s control as is reasonably necessary to enable Supplier to comply with its obligations hereunder.\n" +
                        "11.3.2 Updates and New Versions. In the event that Supplier determines that any of Customer’s reported maintenance problems cannot be resolved due to Customer’s failure to install Updates or procure new Versions of the Software, Customer will be given a reasonable opportunity to install such Updates or procure a new Version. If, after such opportunity, Customer fails or otherwise refuses to install such Updates or procure such new Version, Supplier shall be relieved of its obligations under this clause 11. \n" +
                        "\n" +
                        "11.4 Remedies \n" +
                        "Customer’s sole remedy for Supplier’s material breach of its obligations under this clause 11 will be to have Supplier re-perform the defective services so that they conform to the specifications provided herein.\n" +
                        "\n" +
                        "\n" +
                        "12. INSTALLATION AND TRAINING SERVICES.\n" +
                        "12.1 Installation of the Software\n" +
                        "Installation of all hardware and supporting software so that minimum configuration requirements for installation of the Software are met is the responsibility of Customer and installation of the Software shall also be the responsibility of Customer.\n" +
                        "\n" +
                        "12.2 Training\n" +
                        "Training is offered via a range of methods including webex, face to face and telephone sessions. Customer should contact Supplier to arrange mutually agreeable methods, dates and times.\n" +
                        "\n" +
                        "12.3 Remedies \n" +
                        "Customer’s sole remedy for Supplier’s material breach of its obligations under this clause 12 will be to have Supplier re-perform the defective services so that they conform to the specifications provided herein.\n" +
                        "\n" +
                        " \n" +
                        "\n" +
                        "PART II\n" +
                        "SPECIAL PROVISIONS FOR SPECIFIC SOFTWARE\n" +
                        "Prevail as between Part II and Part I: Certain Software is licensed subject to the provisions of Part II of this Agreement below which augment and/or take precedence over the provisions of Part I in relation to that Software and only to the extent of any conflict or ambiguity.\n" +
                        "\n" +
                        "13. STATUS CHECK\n" +
                        "The following clauses apply to Status Check Software only:\n" +
                        "13.1 Subject to clause 13.2 below, upon expiry or termination of this Agreement (whether for a Trial or paid subscription), Customer’s licence and right to use the Status Check Software or any part thereof shall end immediately and Customer must uninstall the Software and confirm by email to Supplier on trluki.legalonlinenotices@thomsonreuters.com that the said Software has been uninstalled.\n" +
                        "\n" +
                        "13.2 Where Customer subscribes to the Software from the expiry date of any Trial Period, subject to the execution of a new Order Form, Customer shall not be required to uninstall the Software as set out in clause 13.1 above.";
        text.setText(string);
    }
    
}
